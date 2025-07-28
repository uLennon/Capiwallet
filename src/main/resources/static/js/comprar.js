function calcularTotal() {
    var precoCafeManha = 0.70;
    var precoRefeicao = 1.45;

    var qtdCafeManha = Math.max(parseInt(document.getElementById('cafeManha').value) || 0, 0);
    var qtdRefeicao = Math.max(parseInt(document.getElementById('refeicao').value) || 0, 0);

    var total = (qtdCafeManha * precoCafeManha) + (qtdRefeicao * precoRefeicao);
    document.getElementById('total').textContent = 'Total: R$ ' + total.toFixed(2);

    if (qtdCafeManha > 0 || qtdRefeicao > 0) {
        document.getElementById('submitBtn').disabled = false;
    } else {
        document.getElementById('submitBtn').disabled = true;
    }
}

calcularTotal();

function closeModal(element) {
    document.getElementById(element).style.display = "none";
}
function copiarCodigoPix() {
    var pixCode = document.getElementById("codigoPix");
    pixCode.select();
    pixCode.setSelectionRange(0, 99999); 
    document.execCommand("copy");
}

function exibirProcessamento() {
    document.getElementById("modalLoadingMessage").style.display = "block";
}

window.onload = function() {
    const erroPagamento = document.getElementById('errorPagamento');
    const erroGerarPagamento = document.getElementById('errorGerarPagamento');
    if (erroPagamento) {
        erroPagamento.style.display = 'block';
    }
    if(erroGerarPagamento){
        erroGerarPagamento.style.display='block';
    }
};