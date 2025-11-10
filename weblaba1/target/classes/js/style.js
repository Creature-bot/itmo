// js/style.js
let currentPoints = [];
let currentR = 2;
let graphDrawer;

const ALLOWED_R = [1, 1.5, 2, 2.5, 3];
const Y_SCALE = 6;

function hasDecimal() {
    return typeof Decimal !== 'undefined';
}

function toCleanFixed(value, scale = Y_SCALE) {
    try {
        if (hasDecimal()) {
            return new Decimal(value).toFixed(scale, Decimal.ROUND_HALF_UP);
        }
        const num = Number(value);
        if (!Number.isFinite(num)) return '';
        return num.toFixed(scale);
    } catch {
        return '';
    }
}

function toNumberSafe(value, scale = Y_SCALE) {
    const fixed = toCleanFixed(value, scale);
    const n = Number(fixed);
    return Number.isFinite(n) ? n : NaN;
}

function isAllowedR(r) {
    return ALLOWED_R.some(a => Math.abs(a - r) < 1e-9);
}

function normalizeR(r) {
    const n = Number(r);
    return Number.isFinite(n) && isAllowedR(n) ? n : null;
}

function setRUI(r) {
    document.querySelectorAll('input[name="r"]').forEach(input => {
        input.checked = false;
        input.parentElement.classList.remove('selected');
    });
    const target = document.querySelector(`input[name="r"][value="${r}"]`);
    if (target) {
        target.checked = true;
        target.parentElement.classList.add('selected');
    }
}

function formatExecutionTime(us) {
    if (typeof us !== 'number') us = Number(us);
    if (isNaN(us)) return '—';
    if (us >= 1_000_000) return (us / 1_000_000).toFixed(3) + ' s';
    if (us >= 1_000) return (us / 1_000).toFixed(3) + ' ms';
    return us.toFixed(0) + ' μs';
}

// Инициализация
document.addEventListener('DOMContentLoaded', function () {
    console.log('Страница загружена! Инициализация...');
    initApplication();
});

function initApplication() {
    console.log('Инициализация приложения');
    initGraph();
    setupEventListeners();

    setRUI(2);
    document.querySelector('select[name="x"]').value = "";

    drawGraph(2);
}

// График
class GraphDrawer {
    constructor(canvasId) {
        this.canvas = document.getElementById(canvasId);
        this.ctx = this.canvas.getContext('2d');

        this.canvas.width = 400;
        this.canvas.height = 400;
        this.center = {
            x: this.canvas.width / 2,
            y: this.canvas.height / 2
        };
        this.baseScale = this.canvas.width / 3;
        this.currentR = null;
        this.currentPoints = [];
    }

    setCurrentR(r) {
        this.currentR = r;
    }

    setCurrentPoints(points) {
        this.currentPoints = points;
    }

    drawGraph(r = null) {
        this.setCurrentR(r);
        const width = this.canvas.width;
        const height = this.canvas.height;
        const center = this.center;
        const baseScale = this.baseScale;
        const ctx = this.ctx;

        ctx.clearRect(0, 0, width, height);

        // Оси
        ctx.beginPath();
        ctx.strokeStyle = '#0000ff';
        ctx.lineWidth = 3;

        ctx.moveTo(0, center.y);
        ctx.lineTo(width, center.y);
        ctx.moveTo(width, center.y);
        ctx.lineTo(width - 15, center.y - 8);
        ctx.moveTo(width, center.y);
        ctx.lineTo(width - 15, center.y + 8);

        ctx.moveTo(center.x, 0);
        ctx.lineTo(center.x, height);
        ctx.moveTo(center.x, 0);
        ctx.lineTo(center.x - 8, 15);
        ctx.moveTo(center.x, 0);
        ctx.lineTo(center.x + 8, 15);

        ctx.stroke();

        ctx.font = 'bold 14px Arial';
        ctx.fillStyle = '#0000ff';
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';

        const displayR = (this.currentR !== null) ? this.currentR.toString() : 'R';
        const displayRHalf = (this.currentR !== null) ? (this.currentR / 2).toString() : 'R/2';
        const displayRNegHalf = (this.currentR !== null) ? (-this.currentR / 2).toString() : '-R/2';
        const displayRNeg = (this.currentR !== null) ? (-this.currentR).toString() : '-R';

        ctx.fillText(displayR, center.x + baseScale, center.y + 25);
        ctx.fillText(displayRHalf, center.x + baseScale / 2, center.y + 25);
        ctx.fillText(displayRNegHalf, center.x - baseScale / 2, center.y + 25);
        ctx.fillText(displayRNeg, center.x - baseScale, center.y + 25);

        ctx.fillText(displayR, center.x - 25, center.y - baseScale);
        ctx.fillText(displayRHalf, center.x - 25, center.y - baseScale / 2);
        ctx.fillText(displayRNegHalf, center.x - 25, center.y + baseScale / 2);
        ctx.fillText(displayRNeg, center.x - 25, center.y + baseScale);

        // Область
        ctx.fillStyle = 'rgba(255, 0, 255, 0.3)';
        ctx.strokeStyle = '#ff00ff';
        ctx.lineWidth = 2;
        ctx.beginPath();

        ctx.arc(center.x, center.y, baseScale, -Math.PI / 2, 0);
        ctx.lineTo(center.x, center.y);

        ctx.rect(center.x - 0, center.y, -baseScale, -baseScale);

        ctx.moveTo(center.x, center.y);
        ctx.lineTo(center.x - baseScale / 2, center.y);
        ctx.lineTo(center.x, center.y + baseScale / 2);

        ctx.closePath();
        ctx.fill();
        ctx.stroke();

        this.currentPoints.forEach(point => {
            this.drawPoint(point.x, point.y, point.hit);
        });
    }

    drawPoint(x, y, isHit) {
        if (this.currentR === null) return;
        const scaledX = (x * this.baseScale) / this.currentR;
        const scaledY = (y * this.baseScale) / this.currentR;

        this.ctx.beginPath();
        this.ctx.arc(
            this.center.x + scaledX,
            this.center.y - scaledY,
            5,
            0,
            Math.PI * 2
        );
        this.ctx.fillStyle = isHit ? '#00ff00' : '#ff0000';
        this.ctx.fill();
        this.ctx.strokeStyle = '#0000ff';
        this.ctx.lineWidth = 2;
        this.ctx.stroke();
    }

    canvasToGraphCoords(canvasX, canvasY) {
        if (!this.currentR) return null;
        const x = (canvasX - this.center.x) / this.baseScale * this.currentR;
        const y = (this.center.y - canvasY) / this.baseScale * this.currentR;
        return { x, y };
    }
}

function initGraph() {
    graphDrawer = new GraphDrawer('graphCanvas');
}

function drawGraph(r) {
    const valid = normalizeR(r);
    currentR = (valid !== null) ? valid : currentR;
    graphDrawer.setCurrentR(currentR);
    graphDrawer.setCurrentPoints(currentPoints);
    graphDrawer.drawGraph(currentR);
    document.getElementById('currentR').textContent = currentR.toString();
}

//Форма
function setupEventListeners() {
    const form = document.getElementById('pointForm');
    const yInput = document.getElementById('y-input');

    form.addEventListener('submit', function(e) {
        e.preventDefault();
        if (validateForm()) {
            const formData = new FormData(this);
            const params = new URLSearchParams();
            for (const [key, value] of formData) {
                params.append(key, value);
            }
            submitForm(params);
        }
    });

    yInput.addEventListener('input', function () {
        validateYInput(this);
    });

    document.querySelectorAll('input[name="r"]').forEach(radio => {
        radio.addEventListener('change', function () {
            const rValue = normalizeR(this.value);
            if (rValue === null) {
                setRUI(currentR);
                showMessage('Недопустимое значение R', 'error');
                return;
            }
            setRUI(rValue);
            drawGraph(rValue);
        });
    });

    const canvas = document.getElementById('graphCanvas');
    canvas.addEventListener('click', function (e) {
        const rValue = getSelectedR();
        if (!rValue) {
            showMessage('Сначала выберите радиус R', 'error');
            return;
        }

        const rect = canvas.getBoundingClientRect();
        const scaleX = canvas.width / rect.width;
        const scaleY = canvas.height / rect.height;

        const x = (e.clientX - rect.left) * scaleX;
        const y = (e.clientY - rect.top) * scaleY;

        const point = graphDrawer.canvasToGraphCoords(x, y);
        if (!point) return;

        const allowedX = [-5, -4, -3, -2, -1, 0, 1, 2, 3];
        const closestX = allowedX.reduce((prev, curr) =>
            Math.abs(curr - point.x) < Math.abs(prev - point.x) ? curr : prev
        );

        const yClamped = Math.max(-3, Math.min(5, point.y));
        const yFixedStr = toCleanFixed(yClamped, Y_SCALE);

        if (yFixedStr === '') {
            showMessage('Некорректное значение Y', 'error');
            return;
        }

        document.querySelector('select[name="x"]').value = closestX;
        document.getElementById('y-input').value = yFixedStr;

        const formData = new FormData(document.getElementById('pointForm'));
        const params = new URLSearchParams();
        for (const [key, value] of formData) {
            params.append(key, value);
        }

        submitForm(params);
    });
}

function getSelectedR() {
    const selected = document.querySelector('input[name="r"]:checked');
    const r = selected ? normalizeR(selected.value) : null;
    if (r === null) {
        setRUI(currentR);
        return null;
    }
    return r;
}

// Валидация
function validateForm() {
    const xSelect = document.querySelector('select[name="x"]');
    const yInput = document.getElementById('y-input');
    const rValue = getSelectedR();
    let isValid = true;
    let message = '';

    if (!xSelect.value || xSelect.value === "") {
        isValid = false;
        message = 'Выберите координату X';
        xSelect.classList.add('error');
    } else {
        xSelect.classList.remove('error');
    }

    if (isValid) {
        if (!validateYInput(yInput)) {
            isValid = false;
            message = 'Y должен быть числом от -3 до 5';
        }
    }

    if (isValid && !rValue) {
        isValid = false;
        message = 'Выберите радиус R';
    }

    if (!isValid) {
        showMessage(message, 'error');
    }

    return isValid;
}

function validateYInput(input) {
    const raw = input.value.trim();
    const basicRegex = /^-?\d*\.?\d*$/;

    if (!basicRegex.test(raw)) {
        input.classList.add('error');
        return false;
    }

    const n = toNumberSafe(raw, Y_SCALE);
    if (!Number.isFinite(n) || n < -3 || n > 5) {
        input.classList.add('error');
        return false;
    }

    input.value = toCleanFixed(n, Y_SCALE);
    input.classList.remove('error');
    return true;
}

// Запрос/ответ
function submitForm(params) {
    const submitBtn = document.getElementById('submitBtn');
    const originalText = submitBtn.textContent;
    submitBtn.disabled = true;
    submitBtn.textContent = 'Отправка...';

    params.set('r', String(currentR));

    const yRaw = params.get('y');
    const yFixed = toCleanFixed(yRaw, Y_SCALE);
    if (!yFixed) {
        showMessage('Некорректное значение Y', 'error');
        submitBtn.disabled = false;
        submitBtn.textContent = originalText;
        return;
    }
    params.set('y', yFixed);

    fetch('/fcgi-bin/server.jar', {
        method: 'POST',
        body: params,
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    })
        .then(response => response.json())
        .then(data => handleServerResponse(data))
        .catch(error => showMessage('Ошибка сети: ' + error.message, 'error'))
        .finally(() => {
            submitBtn.disabled = false;
            submitBtn.textContent = originalText;
        });
}

function handleServerResponse(data) {
    if (data.success) {
        showMessage(data.message, 'success');
        updateResultsTable(data);
        addPointToGraph(data.currentResult);
    } else {
        showMessage(data.message, 'error');
    }
}

function updateResultsTable(data) {
    const tbody = document.getElementById('resultsBody');
    const result = data.currentResult;

    const yShown = hasDecimal()
        ? new Decimal(result.y).toFixed(Y_SCALE, Decimal.ROUND_HALF_UP)
        : Number(result.y).toFixed(Y_SCALE);

    const row = document.createElement('tr');
    row.innerHTML = `
        <td>${result.x}</td>
        <td>${yShown}</td>
        <td>${result.r}</td>
        <td class="${result.hit ? 'hit-true' : 'hit-false'}">
            ${result.hit ? 'Попадание' : 'Промах'}
        </td>
        <td>${new Date(result.timestamp).toLocaleTimeString()}</td>
        <td>${formatExecutionTime(result.executionTime)}</td>
    `;
    tbody.insertBefore(row, tbody.firstChild);
    if (tbody.children.length > 10) {
        tbody.removeChild(tbody.lastChild);
    }
}

function addPointToGraph(result) {
    const yNum = hasDecimal()
        ? Number(new Decimal(result.y).toFixed(Y_SCALE, Decimal.ROUND_HALF_UP))
        : Number(Number(result.y).toFixed(Y_SCALE));

    const point = {
        x: parseFloat(result.x),
        y: yNum,
        hit: result.hit,
        r: parseFloat(result.r)
    };
    currentPoints.push(point);
    drawGraph(currentR);

    const yShown = hasDecimal()
        ? new Decimal(result.y).toFixed(Y_SCALE, Decimal.ROUND_HALF_UP)
        : Number(result.y).toFixed(Y_SCALE);

    document.getElementById('currentPoint').textContent =
        `X=${result.x}, Y=${yShown}, R=${result.r}`;
}

function showMessage(text, type = 'success') {
    const messageDiv = document.getElementById('message');
    messageDiv.innerHTML = `<div class="message ${type}">${text}</div>`;
    setTimeout(() => {
        messageDiv.innerHTML = '';
    }, 5000);
}

console.log('JavaScript файл загружен!');
