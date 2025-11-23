// main.js
import {GraphDrawer} from './graph.js';
import {
    findClosestValue,
    getPermittedXValues,
    setFormFromPoint,
    setupInputValidation,
    validateInputs
} from './dom-handlers.js';
import {clearResults} from './api.js';
import {showNotification} from './utils.js';

document.addEventListener('DOMContentLoaded', async () => {
    function resetFormFields() {
        document.getElementById('y-input').value = '';
        const xRadios = document.querySelectorAll('input[name="x-value"]');
        xRadios.forEach(radio => {
            radio.checked = false;
        });

        const rCheckboxes = document.querySelectorAll('.r-checkbox');
        rCheckboxes.forEach(checkbox => {
            checkbox.checked = false;
        });
    }

    resetFormFields();

    const graphDrawer = new GraphDrawer('graphCanvas');
    const clearBtn = document.getElementById('clear-btn');
    const form = document.getElementById('main-form');
    const xPreciseInput = document.getElementById('x-precise');
    const rCheckboxes = document.querySelectorAll('.r-checkbox');

    setupInputValidation();

    const loadHistory = async () => {
        const resultsBody = document.querySelector('#results-table tbody');
        const rows = resultsBody.querySelectorAll('tr');
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
        if (graphDrawer.points.length !== 0) {
            const lastPoint = graphDrawer.points[0]
            setFormFromPoint(lastPoint);
            const activeRCheckbox = document.querySelector('input[name="r"]:checked');
            const newR = parseFloat(activeRCheckbox.value);
            graphDrawer.setCurrentR(newR);
        }
        graphDrawer.drawGraph(graphDrawer.currentR);
    };
    await loadHistory();

    form.addEventListener('submit', (event) => {
        const values = validateInputs();
        if (!values) {
            event.preventDefault();
            return;
        }
        const { x, y, r } = values;
        const dataToSend = { x: x.toString(), xPreciseInput: x.toString(), y: y.toString(), r: r.toString() };
        setFormFromPoint(dataToSend);
    });

    graphDrawer.canvas.addEventListener('click', async (event) => {
        const activeRCheckbox = document.querySelector('input[name="r"]:checked');

        if (!activeRCheckbox) {
            showNotification('Для отправки точки с графика выберите R');
            return;
        }

        const currentRValue = new Decimal(activeRCheckbox.value);
        graphDrawer.setCurrentR(parseFloat(activeRCheckbox.value));


        const rect = graphDrawer.canvas.getBoundingClientRect();
        const canvasX = event.clientX - rect.left;
        const canvasY = event.clientY - rect.top;

        const { x: graphX, y: graphY } = graphDrawer.canvasToGraphCoords(canvasX, canvasY);

        const yDecimal = new Decimal(graphY);
        const xDecimal = new Decimal(graphX);

        if (yDecimal.lt(-5) || yDecimal.gt(5)) {
            showNotification('Значение Y вне допустимого диапазона [-5, 5].');
            return;
        }

        if (xDecimal.lt(-4) || xDecimal.gt(4)) {
            showNotification('Значение X вне допустимого диапазона [-4, 4].');
            return;
        }

        const dataToSend = { x: xDecimal.toString(), xPreciseInput: xDecimal.toString(), y: yDecimal.toString(), r: currentRValue.toString() };
        const visibleXInputs = document.querySelectorAll('input[name="x-visible"]');
        visibleXInputs.forEach(input => input.disabled = true);


        try {
            setFormFromPoint(dataToSend);
            form.submit();
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

    clearBtn.addEventListener('click', async () => {
        try {
            const data = await clearResults();
            console.log('Успешная очистка:', data);

            document.querySelector('#results-table tbody').innerHTML = '';
            graphDrawer.clearPoints();
            graphDrawer.drawGraph(graphDrawer.currentR);
            showNotification(data.message, false);
        } catch (error) {
            console.error("Failed to clear results:", error);
        }
    });

    rCheckboxes.forEach(checkbox => {
        checkbox.addEventListener('change', (event) => {
            let newR = null;

            if (event.target.checked) {
                rCheckboxes.forEach(cb => {
                    if (cb !== event.target) {
                        cb.checked = false;
                    }
                });
                newR = parseFloat(event.target.value);
            }

            if (newR !== null && newR >= 1 && newR <= 5) {
                graphDrawer.setCurrentR(newR);
                graphDrawer.drawGraph(newR);
            } else {
                graphDrawer.setCurrentR(null);
                graphDrawer.drawGraph(null);
            }
        });
    });
});
