export class GraphDrawer {
    constructor(canvasId) {
        this.canvas = document.getElementById(canvasId);
        this.ctx = this.canvas.getContext('2d');
        this.canvas.width = 400;
        this.canvas.height = 400;
        this.center = { x: this.canvas.width / 2, y: this.canvas.height / 2 };

        this.pixelR_at_R1 = this.canvas.width * 0.35;
        this.pixelR_at_R5 = this.canvas.width * 0.15;

        this.currentR = null;
        this.points = [];
    }

    clearPoints() {
        this.points = [];
    }

    setCurrentR(r) {
        this.currentR = r;
    }

    addPoint(point) {
        this.points.push(point);
    }

    getPixelR() {
        const dynamicR = this.currentR !== null ? this.currentR : 1;
        const t = (dynamicR - 1) / 4;
        return this.pixelR_at_R1 + t * (this.pixelR_at_R5 - this.pixelR_at_R1);
    }

    graphToCanvas(x, y, r) {
        const pixelR = this.getPixelR();
        const canvasX = (x / r) * pixelR;
        const canvasY = (y / r) * pixelR;

        return {
            x: this.center.x + canvasX,
            y: this.center.y - canvasY
        };
    }

    drawTargetShape(r) {
        if (r === null) return;

        const ctx = this.ctx;
        const pixelR = this.getPixelR();

        ctx.beginPath();
        ctx.fillStyle = 'rgba(244,66,208,0.7)';


        let startPoint = this.graphToCanvas(0, 0, r);
        ctx.moveTo(startPoint.x, startPoint.y);

        let pointR0 = this.graphToCanvas(r, 0, r);
        ctx.lineTo(pointR0.x, pointR0.y);

        let pointRR = this.graphToCanvas(r, r, r);
        ctx.lineTo(pointRR.x, pointRR.y);

        let point0R = this.graphToCanvas(0, r, r);
        ctx.lineTo(point0R.x, point0R.y);

        let pointMinusR0 = this.graphToCanvas(-r, 0, r);
        ctx.lineTo(pointMinusR0.x, pointMinusR0.y);

        ctx.lineTo(startPoint.x, startPoint.y);

        ctx.closePath();
        ctx.fill();

        ctx.beginPath();
        ctx.fillStyle = 'rgba(244,66,208,0.7)';

        let point0MinusR = this.graphToCanvas(0, -r, r);
        ctx.moveTo(point0MinusR.x, point0MinusR.y);

        ctx.arc(
            this.center.x,
            this.center.y,
            pixelR,
            Math.PI * 0.5,
            Math.PI,
            false
        );

        ctx.lineTo(startPoint.x, startPoint.y);
        ctx.lineTo(point0MinusR.x, point0MinusR.y);

        ctx.closePath();
        ctx.fill();
    }

    drawGraph(r = null) {
        this.setCurrentR(r);
        const width = this.canvas.width;
        const height = this.canvas.height;
        const center = this.center;
        const ctx = this.ctx;

        ctx.clearRect(0, 0, width, height);

        ctx.beginPath();
        ctx.strokeStyle = '#000';
        ctx.lineWidth = 3;

        ctx.moveTo(0, center.y);
        ctx.lineTo(width, center.y);

        ctx.moveTo(center.x, 0);
        ctx.lineTo(center.x, height);

        ctx.moveTo(width, center.y);
        ctx.lineTo(width - 25, center.y - 12);
        ctx.moveTo(width, center.y);
        ctx.lineTo(width - 25, center.y + 12);

        ctx.moveTo(center.x, 0);
        ctx.lineTo(center.x - 12, 25);
        ctx.moveTo(center.x, 0);
        ctx.lineTo(center.x + 12, 25);
        ctx.stroke();

        const dynR = this.currentR !== null ? this.currentR : 1;
        const R_range = 5 - 1;
        const Font_range = 20 - 12;
        const t = (dynR - 1) / R_range;
        const dynamicFontSize = 20 - t * Font_range;

        ctx.font = `${dynamicFontSize.toFixed(0)}px Arial`;
        ctx.fillStyle = '#000';
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';

        const dynamicR = this.currentR !== null ? this.currentR : 1;
        const pixelR = this.getPixelR();
        const pixelRHalf = pixelR / 2;

        const rLabel = this.currentR !== null ? dynamicR.toFixed(1) : "R";
        const rHalfLabel = this.currentR !== null ? (dynamicR / 2).toFixed(1) : "R/2";

        ctx.fillText("X", width - 20, center.y + 30);
        ctx.fillText("Y", center.x - 30, 20);

        ctx.fillText(rLabel, center.x + pixelR, center.y + 30);
        ctx.fillText(rHalfLabel, center.x + pixelRHalf, center.y + 30);
        ctx.fillText(`-${rHalfLabel}`, center.x - pixelRHalf, center.y + 30);
        ctx.fillText(`-${rLabel}`, center.x - pixelR, center.y + 30);

        ctx.fillText(rLabel, center.x - 30, center.y - pixelR);
        ctx.fillText(rHalfLabel, center.x - 30, center.y - pixelRHalf);
        ctx.fillText(`-${rHalfLabel}`, center.x - 30, center.y + pixelRHalf);
        ctx.fillText(`-${rLabel}`, center.x - 30, center.y + pixelR);

        this.drawTargetShape(dynamicR);

        this.points.forEach(point => {
            this.drawPoint(point.x, point.y, point.isHit, point.r);
        });
    }

    drawPoint(x, y, isHit, r) {
        if (this.currentR === null) {
            return;
        }

        const center = this.center;
        const ctx = this.ctx;
        const pixelR = this.getPixelR();

        const scaledX = (x / r) * this.currentR;
        const scaledY = (y / r) * this.currentR;

        const canvasX = (scaledX / this.currentR) * pixelR;
        const canvasY = (scaledY / this.currentR) * pixelR;

        ctx.beginPath();
        ctx.arc(
            center.x + canvasX,
            center.y - canvasY,
            3,
            0,
            Math.PI * 2
        );
        ctx.fillStyle = isHit ? 'green' : 'red';
        ctx.fill();
    }

    clearDynamicElements() {
        this.drawGraph(this.currentR);
    }

    drawMouseLines(mouseX, mouseY, displayX, displayY) {
        this.clearDynamicElements();

        const ctx = this.ctx;
        const canvas = this.canvas;

        ctx.beginPath();
        ctx.strokeStyle = 'rgba(0, 0, 0, 0.5)';
        ctx.lineWidth = 1;

        ctx.moveTo(mouseX, 0);
        ctx.lineTo(mouseX, canvas.height);

        ctx.moveTo(0, mouseY);
        ctx.lineTo(canvas.width, mouseY);
        ctx.stroke();

        ctx.font = '14px Arial';
        ctx.fillStyle = '#000';
        ctx.textAlign = 'left';
        ctx.textBaseline = 'bottom';

        ctx.fillText(`X: ${displayX.toFixed(2)}`, mouseX + 10, mouseY - 10);
        ctx.fillText(`Y: ${displayY.toFixed(2)}`, mouseX + 10, mouseY + 20);
    }

    clearMouseLines() {
        this.clearDynamicElements();
    }

    canvasToGraphCoords(canvasX, canvasY) {
        if (this.currentR === null) {
            return { x: 0, y: 0 };
        }

        const t = (this.currentR - 1) / 4;
        const pixelR = this.pixelR_at_R1 + t * (this.pixelR_at_R5 - this.pixelR_at_R1);

        const x = ((canvasX - this.center.x) * this.currentR) / pixelR;
        const y = ((this.center.y - canvasY) * this.currentR) / pixelR;

        return { x, y };
    }
}
