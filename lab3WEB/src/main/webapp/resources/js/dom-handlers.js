
function setupInputValidation(input) {
    const yInput = document.getElementById('main-form:y-input');

    const validateInput = (input) => {
        input.addEventListener('input', function() {
            this.value = this.value.replace(/[^0-9.,-]/g, '');
            this.value = this.value.replace(/,/g, '.');
            if ((this.value.match(/\./g) || []).length > 1) {
                this.value = this.value.substring(0, this.value.lastIndexOf('.'));
            }
            if (this.value.indexOf('-') > 0) {
                this.value = this.value.replace(/-/g, '');
            }
            if (this.value.length > 1 && this.value.includes('-')) {
                this.value = this.value.replace(/-/g, '');
                this.value = '-' + this.value;
            }
        });
    };

    validateInput(yInput);
}

function getPermittedXValues() {
    return [-3, -2, -1, 0, 1, 2, 3];
}

const ALLOWED_VALUES = [
    new Decimal(1),
    new Decimal(1.5),
    new Decimal(2),
    new Decimal(2.5),
    new Decimal(3)
];

function validateInputs() {
    let xBtn = document.querySelector('input[name="x-visible"]:checked')?.value;
    const yInput = document.getElementById('y-input');
    const rInput = document.getElementById('r-input');

    const permittedX = getPermittedXValues();
    const yValue = yInput.value.replace(/,/g, '.');
    const rValue = rInput.value.replace(/,/g, '.');

    try {
        const y = new Decimal(yValue);
        const r = new Decimal(rValue);

        const isValid = ALLOWED_VALUES.some(allowedR => r.equals(allowedR));

        if (!xBtn) {
            showNotification('Пожалуйста выберите X');
            return null;
        }
        xBtn = xBtn.replace(',', '.');

        const x = parseFloat(xBtn);

        if (isNaN(x) || !permittedX.includes(x)) {
            showNotification('Баловаться запрещено!, выберите подходящий X');
            return null;
        }

        if (y.isNaN() || !y.isFinite()) {
            showNotification('Введите корректное числовое значение Y');
            yInput.focus();
            return null;
        }

        if (y.lt(-5) || y.gt(5)) {
            showNotification('Введите Y в промежутке от -5 до 5');
            yInput.focus();
            return null;
        }

        if (r.isNaN() || !r.isFinite()) {
            showNotification('Введите корректное числовое значение R');
            rInput.focus();
            return null;
        }

        if (!isValid) {
            showNotification('Значение r должно быть 1, 1.5, 2, 2.5 или 3.');
            rInput.focus();
            return null;
        }

        return { x, y, r };
    } catch (error) {
        showNotification('Введите корректный формат Y и R');
        return null;
    }
}


function setFormFromPoint(point) {
    const xBut = document.getElementById('graph-form:x-graph-hidden');
    const yBut = document.getElementById('graph-form:y-graph-hidden');
    const rBut = document.getElementById('graph-form:r-graph-hidden');
    const yButReal = document.getElementById('main-form:y-input');

    yButReal.value = point.y;
    xBut.value = point.x;
    yBut.value = point.y;
    rBut.value = point.r;

    if (!isNaN(point.r) && (point.r == 1 || point.r == 1.5 || point.r == 2 || point.r == 2.5 || point.r == 3)) {
        graphDrawer.setCurrentR(parseFloat(point.r));
        graphDrawer.drawGraph(parseFloat(point.r));
    }
}