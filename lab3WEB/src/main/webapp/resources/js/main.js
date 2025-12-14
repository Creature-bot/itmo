const graphDrawer = new GraphDrawer('graphCanvas');

document.getElementById("main-form:y-input").addEventListener("input", (ev) => {
    controlYInput(ev);
});

function controlYInput(ev) {
    let value = ev.target.value;

    value = value.replace(/[^0-9.,-]/g, '');
    value = value.replace(/,/g, '.');
    if ((value.match(/\./g) || []).length > 1) {
        value = value.substring(0, value.lastIndexOf('.'));
    }

    if (value.indexOf('-') > 0) {
        value = value.replace(/-/g, '');
    }
    if (value.length > 1 && value.includes('-')) {
        let cleanValue = value.replace(/-/g, '');
        value = '-' + cleanValue;
    }

    ev.target.value = value;
}

function attachYInputValidator() {
    const yInput = document.getElementById("main-form:y-input");

    if (yInput) {
        yInput.removeEventListener('input', controlYInput);

        yInput.addEventListener('input', controlYInput);
    }
}

function handleRChangeComplete(newR) {
    if (!isNaN(newR) && (newR == 1 || newR == 1.5 || newR == 2 || newR == 2.5 || newR == 3)) {
        graphDrawer.setCurrentR(newR);
        graphDrawer.drawGraph(newR);
    } else {
        graphDrawer.setCurrentR(null);
        graphDrawer.drawGraph(null);
    }
}

function handleClearComplete() {
    graphDrawer.clearPoints();
    graphDrawer.setCurrentR(null);
    graphDrawer.drawGraph(null);

    const resultsBody = document.querySelector('#results-table tbody');
    const rowElement = resultsBody.querySelector('tr:first-child');
    const cells = rowElement.querySelectorAll('td');
    if (cells.length < 3) {
        showNotification('Успешная очистка', false);
    }
    attachYInputValidator();
}

function handleSbmComplete() {
    const resultsBody = document.querySelector('#results-table tbody');
    const rowElement = resultsBody.querySelector('tr:first-child');
    const cells = rowElement.querySelectorAll('td');

    if (cells.length > 3) {
        const x = parseFloat(cells[0].textContent);
        const y = parseFloat(cells[1].textContent);
        const r = parseFloat(cells[2].textContent);

        const isHit = cells[3].textContent.trim() === 'Попал';

        graphDrawer.addPoint({
            x,
            y,
            isHit,
            r
        });

        const newR = parseFloat(document.getElementById('main-form:r-input').value);
        if (!isNaN(newR) && (newR == 1 || newR == 1.5 || newR == 2 || newR == 2.5 || newR == 3)) {
            graphDrawer.setCurrentR(newR);
            graphDrawer.drawGraph(newR);
        }
    }
    attachYInputValidator();
}

document.addEventListener('DOMContentLoaded', async () => {
    const rInput = document.getElementById('main-form:r-input');
    rInput.value = rInput.getAttribute('value');

    const loadHistory = async () => {
        const resultsBody = document.querySelector('#results-table tbody');
        const rows = resultsBody.querySelectorAll('tr');

        graphDrawer.clearPoints();

        rows.forEach(row => {
            const cells = row.querySelectorAll('td');
            if (cells.length > 3) {
                const x = parseFloat(cells[0].textContent);
                const y = parseFloat(cells[1].textContent);
                const r = parseFloat(cells[2].textContent);
                const isHit = cells[3].textContent.trim() === 'Попал';

                graphDrawer.addPoint({
                    x,
                    y,
                    isHit,
                    r
                });
            }
        });

        if (rInput.value !== "") {
            const currentR = parseFloat(rInput.value);
            graphDrawer.setCurrentR(currentR);
            graphDrawer.drawGraph(currentR);
        } else {
            graphDrawer.setCurrentR(null);
            graphDrawer.drawGraph(null);
        }
    };

    await loadHistory();


    graphDrawer.canvas.addEventListener('click', async (event) => {
        if (graphDrawer.currentR === null) {
            showNotification('Для отправки точки с графика выберите R');
            return;
        }

        const activeRButton = document.getElementById('main-form:r-input');
        const submitButton = document.getElementById('graph-form:sub-btn');


        const rect = graphDrawer.canvas.getBoundingClientRect();
        const canvasX = event.clientX - rect.left;
        const canvasY = event.clientY - rect.top;


        const { x: graphX, y: graphY } = graphDrawer.canvasToGraphCoords(canvasX, canvasY);

        const currentRValue = new Decimal(activeRButton.value);
        const yDecimal = new Decimal(graphY);
        const xDecimal = new Decimal(graphX);



        if (yDecimal.lt(-5) || yDecimal.gt(5)) {
            showNotification('Значение Y вне допустимого диапазона [-5, 5].');
            return;
        }

        const dataToSend = { y: yDecimal.toString(), x: xDecimal.toString(), r: currentRValue.toString()};

        try {
            setFormFromPoint(dataToSend);
            submitButton.click();
        } catch (error) {
            console.error('Ошибка при отправке координат:', error);
        }
    });


    graphDrawer.canvas.addEventListener('mousemove', (event) => {
        if (graphDrawer.currentR === null) {
            return;
        }

        const rect = graphCanvas.getBoundingClientRect();
        const mouseX = event.clientX - rect.left;
        const mouseY = event.clientY - rect.top;

        const coords = graphDrawer.canvasToGraphCoords(mouseX, mouseY);
        graphDrawer.drawMouseLines(mouseX, mouseY, coords.x, coords.y);
    });

    graphDrawer.canvas.addEventListener('mouseout', () => {
        graphDrawer.clearMouseLines();
    });
    attachYInputValidator();
});
