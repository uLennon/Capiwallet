document.getElementById('btn-trocar-senha').addEventListener('click', function() {
    document.getElementById('modal-senha').style.display = 'block';
});

document.getElementById('close-senha').addEventListener('click', function() {
    document.getElementById('modal-senha').style.display = 'none';
});

document.getElementById('btn-trocar-email').addEventListener('click', function() {
    document.getElementById('modal-email').style.display = 'block';
});

document.getElementById('close-email').addEventListener('click', function() {
    document.getElementById('modal-email').style.display = 'none';
});

document.getElementById('close-historico').addEventListener('click', function() {
    document.getElementById('modal-historico').style.display = 'none';
});

window.addEventListener('click', function(event) {
    if (event.target == document.getElementById('modal-senha')) {
        document.getElementById('modal-senha').style.display = 'none';
    }
    if (event.target == document.getElementById('modal-email')) {
        document.getElementById('modal-email').style.display = 'none';
    }
});

function submitForm() {
    const fileInput = document.getElementById('file');
    const fileNameSpan = document.getElementById('file-name');

    if (fileInput.files.length > 0) {
        fileNameSpan.textContent = fileInput.files[0].name;
        document.getElementById('imageUploadForm').submit();
    }
}
