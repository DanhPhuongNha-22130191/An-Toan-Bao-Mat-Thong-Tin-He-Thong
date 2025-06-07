document.addEventListener('DOMContentLoaded', () => {
    const addFormContainer = document.getElementById('addFormContainer');

    const addOverlay = document.createElement('div');
    addOverlay.className = 'modal-overlay';
    const addModalContent = document.createElement('div');
    addModalContent.className = 'modal-content';

    const addCloseButton = document.createElement('button');
    addCloseButton.className = 'close-modal';
    addCloseButton.innerHTML = '&times;';
    addCloseButton.style.position = 'absolute';
    addCloseButton.style.top = '10px';
    addCloseButton.style.right = '15px';

    const addForm = addFormContainer.querySelector('form');
    addModalContent.appendChild(addCloseButton);
    addModalContent.appendChild(addForm);
    addOverlay.appendChild(addModalContent);
    document.body.appendChild(addOverlay);

    document.querySelector('button.btn-primary').addEventListener('click', () => {
        addOverlay.classList.add('active');
    });

    addCloseButton.addEventListener('click', () => {
        addOverlay.classList.remove('active');
    });

    document.getElementById('cancelAddForm').addEventListener('click', () => {
        addOverlay.classList.remove('active');
    });

    // Ảnh xem trước
    document.getElementById('add-image').addEventListener('change', function () {
        const file = this.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                const preview = document.getElementById('addImagePreview');
                preview.src = e.target.result;
                preview.style.display = 'block';
            };
            reader.readAsDataURL(file);
        }
    });
});
