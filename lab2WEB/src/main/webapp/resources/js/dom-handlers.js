import { showNotification } from './utils.js';


export function setupInputValidation() {
    const yInput = document.getElementById('y-input');

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

export function getPermittedXValues() {
    return [-4, -3, -2, -1, 0, 1, 2, 3, 4];
}

export function getPermittedRValues() {
    return [1, 2, 3, 4, 5];
}

export function validateInputs() {
    let xBtn = document.querySelector('input[name="x-visible"]:checked')?.value;
    const rBtn = document.querySelector('input[name="r"]:checked')?.value;
    const yInput = document.getElementById('y-input');

    const permittedX = getPermittedXValues();
    const yValue = yInput.value.replace(/,/g, '.');
    const permittedR = getPermittedRValues();

    try {
        const y = new Decimal(yValue);

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

        if (!rBtn) {
            showNotification('Пожалуйста выберите R');
            return null;
        }
        const rValue = rBtn.replace(',', '.');
        const r = new Decimal(rValue);

        if (isNaN(parseFloat(rBtn)) || !permittedR.includes(parseFloat(rBtn))) {
            showNotification('Баловаться запрещено!, выберите подходящий R');
            return null;
        }

        return { x, y, r };
    } catch (error) {
        showNotification('Введите корректный формат Y и R');
        return null;
    }
}


export function setFormFromPoint(point) {
    const xRadio = document.querySelector(`input[name="x-visible"][value="${point.x}"]`);
    if (xRadio) {
        xRadio.checked = true;
    }

    const xPreciseInput = document.getElementById('x');
    if (xPreciseInput) {
        xPreciseInput.value = point.xPreciseInput;
    }

    const yInput = document.getElementById('y-input');
    if (yInput) {
        yInput.value = point.y;
    }

    const rCheckbox = document.querySelector(`input[name="r"][value="${point.r}"]`);
    if (rCheckbox) {
        rCheckbox.checked = true;
    }

    document.querySelectorAll('input[name="r"]').forEach(cb => {
        if (cb !== rCheckbox) {
            cb.checked = false;
        }
    });
}

export function findClosestValue(value, array) {
    return array.reduce((prev, curr) =>
        Math.abs(curr - value) < Math.abs(prev - value) ? curr : prev
    );
}
