// api.js
import { showNotification } from './utils.js';

export async function clearResults() {
    try {
        const response = await fetch('http://localhost:4555/controller', {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Ошибка при очистке:', error);
        showNotification('Ошибка при очистке результатов');
        throw error;
    }
}
