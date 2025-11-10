// js/style.js
let currentPoints = [];
let currentR = 2;
let graphDrawer;

const ALLOWED_R = [1, 1.5, 2, 2.5, 3];
const Y_SCALE = 6;

function hasDecimal() {
// что такое typeof и как работает
	return typeof Decimal !== "undefined";
}

function stripTrailingZeros(s) {
	return s.replace(/(?:\.0+|(\.\d*?[1-9])0+)$/, "$1");
}

function toRoundedString(value, scale = Y_SCALE) {
	if (hasDecimal()) {
		const d = new Decimal(value);
		const fixed = d.toFixed(scale, Decimal.ROUND_HALF_UP);
		return stripTrailingZeros(fixed);
	} else {
		const num = Number(value);
		if (!Number.isFinite(num)) return "";
		return stripTrailingZeros(num.toFixed(scale));
	}
}

function toNumberFromString(raw) {
	if (typeof raw !== "string") return NaN;

	const sanitized = raw.replace(",", ".");

	if (hasDecimal()) {
		try {
			const d = new Decimal(sanitized);
			if (!d.isFinite()) return NaN;
			return d;
		} catch (e) {
			return NaN;
		}
	} else {
		const n = Number(sanitized);
		return Number.isFinite(n) ? n : NaN;
	}
}


function formatExecutionTime(us) {
	if (typeof us !== "number") us = Number(us);
	if (isNaN(us)) return "—";
	if (us >= 1_000_000) return (us / 1_000_000).toFixed(3) + " s";
	if (us >= 1_000) return (us / 1_000).toFixed(3) + " ms";
	return us.toFixed(0) + " μs";
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
		input.parentElement.classList.remove("selected");
	});
	const target = document.querySelector(`input[name="r"][value="${r}"]`);
	if (target) {
		target.checked = true;
		target.parentElement.classList.add("selected");
	}
}


// Инициализация
// что делает, и зачем именно нужен
document.addEventListener("DOMContentLoaded", function () {
	console.log("Страница загружена! Инициализация...");
	initApplication();
});

function initApplication() {
	console.log("Инициализация приложения");
	initGraph();
	setupEventListeners();

	setRUI(2);
	document.querySelector('select[name="x"]').value = "";

	drawGraph(2);
}


// График
// Рассказать про устройство классов в javascript
class GraphDrawer {
	constructor(canvasId) {
		this.canvas = document.getElementById(canvasId);
		this.ctx = this.canvas.getContext("2d");

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
		ctx.strokeStyle = "#0000ff";
		ctx.lineWidth = 3;

		// OX
		ctx.moveTo(0, center.y);
		ctx.lineTo(width, center.y);
		ctx.moveTo(width, center.y);
		ctx.lineTo(width - 15, center.y - 8);
		ctx.moveTo(width, center.y);
		ctx.lineTo(width - 15, center.y + 8);

		// OY
		ctx.moveTo(center.x, 0);
		ctx.lineTo(center.x, height);
		ctx.moveTo(center.x, 0);
		ctx.lineTo(center.x - 8, 15);
		ctx.moveTo(center.x, 0);
		ctx.lineTo(center.x + 8, 15);

		ctx.stroke();

		ctx.font = "bold 14px Arial";
		ctx.fillStyle = "#0000ff";
		ctx.textAlign = "center";
		ctx.textBaseline = "middle";

		const displayR = (this.currentR !== null) ? this.currentR.toString() : "R";
		const displayRHalf = (this.currentR !== null) ? (this.currentR / 2).toString() : "R/2";
		const displayRNegHalf = (this.currentR !== null) ? (-this.currentR / 2).toString() : "-R/2";
		const displayRNeg = (this.currentR !== null) ? (-this.currentR).toString() : "-R";

		ctx.fillText(displayR, center.x + baseScale, center.y + 25);
		ctx.fillText(displayRHalf, center.x + baseScale / 2, center.y + 25);
		ctx.fillText(displayRNegHalf, center.x - baseScale / 2, center.y + 25);
		ctx.fillText(displayRNeg, center.x - baseScale, center.y + 25);

		ctx.fillText(displayR, center.x - 25, center.y - baseScale);
		ctx.fillText(displayRHalf, center.x - 25, center.y - baseScale / 2);
		ctx.fillText(displayRNegHalf, center.x - 25, center.y + baseScale / 2);
		ctx.fillText(displayRNeg, center.x - 25, center.y + baseScale);

		// Область (I: четверть круга, II: прямоуг., III: треугольник)
		ctx.fillStyle = "rgba(255, 0, 255, 0.3)";
		ctx.strokeStyle = "#ff00ff";
		ctx.lineWidth = 2;
		ctx.beginPath();

		// I четверть — круг
		ctx.arc(center.x, center.y, baseScale, -Math.PI / 2, 0);
		ctx.lineTo(center.x, center.y);

		// II четверть — прямоугольник
		ctx.rect(center.x - 0, center.y, -baseScale, -baseScale);

		// III четверть — треугольник
		ctx.moveTo(center.x, center.y);
		ctx.lineTo(center.x - baseScale / 2, center.y);
		ctx.lineTo(center.x, center.y + baseScale / 2);

		ctx.closePath();
		ctx.fill();
		ctx.stroke();

		// Точки
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
		this.ctx.fillStyle = isHit ? "#00ff00" : "#ff0000";
		this.ctx.fill();
		this.ctx.strokeStyle = "#0000ff";
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
	graphDrawer = new GraphDrawer("graphCanvas");
}

function drawGraph(r) {
	const valid = normalizeR(r);
	currentR = (valid !== null) ? valid : currentR;
	graphDrawer.setCurrentR(currentR);
	graphDrawer.setCurrentPoints(currentPoints);
	graphDrawer.drawGraph(currentR);
	const rSpan = document.getElementById("currentR");
	if (rSpan) rSpan.textContent = currentR.toString();
}


// Форма
function setupEventListeners() {
	const form = document.getElementById("pointForm");
	const yInput = document.getElementById("y-input");

// какие есть типы событий у разных элементов, рассказать про слушатели
	form.addEventListener("submit", function (e) {
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

	yInput.addEventListener("input", function () {
		validateYInputSoft(this);
	});

	yInput.addEventListener("blur", function () {
		normalizeYOnBlur(this);
	});

	document.querySelectorAll('input[name="r"]').forEach(radio => {
		radio.addEventListener("change", function () {
			const rValue = normalizeR(this.value);
			if (rValue === null) {
				setRUI(currentR);
				showMessage("Недопустимое значение R", "error");
				return;
			}
			setRUI(rValue);
			drawGraph(rValue);
		});
	});

	const canvas = document.getElementById("graphCanvas");
	canvas.addEventListener("click", function (e) {
		const rValue = getSelectedR();
		if (!rValue) {
			showMessage("Сначала выберите радиус R", "error");
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

		const yParsed = toNumberFromString(String(point.y));
        if (!yParsed || (hasDecimal() && !Decimal.isDecimal(yParsed))) {
        	showMessage("Некорректное значение Y", "error");
        	return;
        }

        if (hasDecimal()) {
        	if (yParsed.lessThan(-3) || yParsed.greaterThan(5)) {
        		showMessage("Y должен быть строго между -3 и 5", "error");
        		return;
        	}
        } else {
        	if (yParsed < -3 || yParsed > 5) {
        		showMessage("Y должен быть строго между -3 и 5", "error");
        		return;
        	}
        }

        document.querySelector('select[name="x"]').value = closestX;
        document.getElementById("y-input").value = toRoundedString(yParsed, Y_SCALE);

        const formData = new FormData(document.getElementById("pointForm"));
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


function isIntermediateValue(v) {
	return v === "" || v === "-" || v === "." || v === "-.";
}

function validateYInputSoft(input) {
	const raw = input.value.trim();
	if (isIntermediateValue(raw)) {
		input.classList.remove("error");
		return true;
	}

	const basicRegex = /^-?\d*[.,]?\d*$/;
	if (!basicRegex.test(raw)) {
		input.classList.add("error");
		return false;
	}

	const parsed = toNumberFromString(raw);

	if (!parsed || (hasDecimal() && !Decimal.isDecimal(parsed))) {
		input.classList.add("error");
		return false;
	}

	if (hasDecimal()) {
		if (parsed.lessThan(-3) || parsed.greaterThan(5)) {
			input.classList.add("error");
			return false;
		}
	} else {
		if (parsed < -3 || parsed > 5) {
			input.classList.add("error");
			return false;
		}
	}

	input.classList.remove("error");
	return true;
}


function normalizeYOnBlur(input) {
	const raw = input.value.trim();
	if (isIntermediateValue(raw)) return;

	const parsed = toNumberFromString(raw);
	if (!parsed || (hasDecimal() && !Decimal.isDecimal(parsed))) return;

	if (hasDecimal()) {
		if (parsed.lessThan(-3) || parsed.greaterThan(5)) return;
		input.value = toRoundedString(parsed, Y_SCALE);
	} else {
		if (parsed < -3 || parsed > 5) return;
		input.value = toRoundedString(parsed, Y_SCALE);
	}
}



// Валидация формы
function validateForm() {
	const xSelect = document.querySelector('select[name="x"]');
	const yInput = document.getElementById("y-input");
	const rValue = getSelectedR();
	let isValid = true;
	let message = "";

	if (!xSelect.value || xSelect.value === "") {
		isValid = false;
		message = "Выберите координату X";
		xSelect.classList.add("error");
	} else {
		xSelect.classList.remove("error");
	}

	if (isValid) {
		if (!validateYInputSoft(yInput)) {
			isValid = false;
			message = "Y должен быть числом от -3 до 5";
		}
	}

	if (isValid && !rValue) {
		isValid = false;
		message = "Выберите радиус R";
	}

	if (!isValid) {
		showMessage(message, "error");
	}

	return isValid;
}

function safeToFloatForJSON(value, scale = Y_SCALE) {
	try {
		let d;

		if (hasDecimal()) {
			if (value instanceof Decimal) {
				d = value;
			} else {
				d = new Decimal(String(value).replace(",", "."));
			}

			if (!d.isFinite()) return null;
			if (d.lessThan(-3) || d.greaterThan(5)) return null;

			return Number(d.toFixed(scale, Decimal.ROUND_HALF_UP));
		} else {
			const n = Number(String(value).replace(",", "."));
			if (!Number.isFinite(n)) return null;
			if (n < -3 || n > 5) return null;
			return Number(n.toFixed(scale));
		}
	} catch (e) {
		return null;
	}
}

// Запрос/ответ
function submitForm(params) {
	const submitBtn = document.getElementById("submitBtn");
	const originalText = submitBtn.textContent;
	submitBtn.disabled = true;
	submitBtn.textContent = "Отправка...";

	params.set("r", String(currentR));
	const yRaw = (params.get("y") || "").trim();

	const yParsed = safeToFloatForJSON(yRaw);

	if (yParsed === null) {
		showMessage("Y должен быть строго между -3 и 5", "error");
		submitBtn.disabled = false;
		submitBtn.textContent = originalText;
		return;
	}

	params.set("y", yParsed.toString());

	fetch("/fcgi-bin/server.jar", {
		method: "POST",
		body: params,
		// зачем заголовок (этот именно), что обозначает
		headers: {
			"Content-Type": "application/x-www-form-urlencoded"
		}
	})
		.then(response => response.json())
		.then(data => handleServerResponse(data))
		.catch(error => showMessage("Ошибка сети: " + error.message, "error"))
		.finally(() => {
			submitBtn.disabled = false;
			submitBtn.textContent = originalText;
		});
}


function handleServerResponse(data) {
	if (data.success) {
		showMessage(data.message, "success");
		updateResultsTable(data);
		addPointToGraph(data.currentResult);
	} else {
		showMessage(data.message, "error");
	}
}

// Таблица истории
function updateResultsTable(data) {
	const tbody = document.getElementById("resultsBody");
	const result = data.currentResult;

	const yShown = toRoundedString(result.y, Y_SCALE);
	const row = document.createElement("tr");
	row.innerHTML = `
		<td>${result.x}</td>
		<td>${yShown}</td>
		<td>${result.r}</td>
		<td class="${result.hit ? "hit-true" : "hit-false"}">
			${result.hit ? "Попадание" : "Промах"}
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
	const yShown = toRoundedString(result.y, Y_SCALE);
	const yNum = Number(yShown);

	currentPoints.push({
		x: parseFloat(result.x),
		y: yNum,
		hit: result.hit,
		r: parseFloat(result.r)
	});
	drawGraph(currentR);

	const pointSpan = document.getElementById("currentPoint");
	if (pointSpan) {
		pointSpan.textContent = `X=${result.x}, Y=${yShown}, R=${result.r}`;
	}
}

function showMessage(text, type = "success") {
	const messageDiv = document.getElementById("message");
	messageDiv.innerHTML = `<div class="message ${type}">${text}</div>`;
	// что такое? что принимает как аргументы
	setTimeout(() => {
		messageDiv.innerHTML = "";
	}, 5000);
}

console.log("JavaScript файл загружен!");
