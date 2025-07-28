package com.digital.wallet.services;

import com.digital.wallet.model.CompraPresencial;
import com.digital.wallet.model.Usuario;
import com.digital.wallet.repositories.CompraPresencialRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;


@Service
public class CompraService {

    private final CompraPresencialRepository compraPresencialRepository;

    public CompraService(CompraPresencialRepository compraPresencialRepository) {
        this.compraPresencialRepository = compraPresencialRepository;
    }

    public List<CompraPresencial> buscarComprasPresenciais() {
        return compraPresencialRepository.findAll();
    }

    public void salvaCompra(String matricula, int quantidadeCafe, int quantidadeRefeicao, String nomeTecnico, Usuario usuario) {
        CompraPresencial compraPresencial = CompraPresencial.builder()
                .matricula(matricula)
                .quantidadeTicketCafeManha(quantidadeCafe)
                .quantidadeTicketRefeicao(quantidadeRefeicao)
                .nomeVendedor(nomeTecnico)
                .usuario(usuario)
                .build();
        compraPresencial.setData();
        compraPresencial.setValor();

        compraPresencialRepository.save(compraPresencial);
    }

    public void exportarPDF(HttpServletResponse response) {
        List<CompraPresencial> comprasPresenciais = buscarComprasPresenciais();

        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"compras.pdf\"");
            PdfWriter escritor = new PdfWriter(response.getOutputStream());
            PdfDocument pdf = new PdfDocument(escritor);
            Document documento = new Document(pdf);
            BigDecimal valorDasVendas = new BigDecimal(0);

            documento.add(new Paragraph("Relatório de Compras").setBold().setFontSize(14));

            Table tabela = new Table(6);

            tabela.addHeaderCell("Data");
            tabela.addHeaderCell("Nome do Vendedor");
            tabela.addHeaderCell("Matrícula");
            tabela.addHeaderCell("Quantidade de Tickets de Almoço");
            tabela.addHeaderCell("Quantidade de Tickets de Café de Manhã");
            tabela.addHeaderCell("Valor Total");

            for (CompraPresencial compra : comprasPresenciais) {
                tabela.addCell(compra.getData());
                tabela.addCell(compra.getNomeVendedor());
                tabela.addCell(compra.getMatricula());
                tabela.addCell(String.valueOf(compra.getQuantidadeTicketRefeicao()));
                tabela.addCell(String.valueOf(compra.getQuantidadeTicketCafeManha()));
                tabela.addCell(compra.getValor().toString());

                valorDasVendas = valorDasVendas.add(compra.getValor());
            }
            documento.add(tabela);
            documento.add(new Paragraph("Valor total de Vendas: " + valorDasVendas));
            documento.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void exportarExcel(HttpServletResponse response) {
        List<CompraPresencial> comprasPresenciais = buscarComprasPresenciais();

        if (comprasPresenciais.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        }

        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=\"compras.xlsx\"");

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Compras");

                Row linhaCabecalho = sheet.createRow(0);
                String[] cabecalhos = { "Data", "Nome do vendedor", "Matrícula", "Quantidade de Tickets de Almoço", "Quantidade de Tickets de Café de Manhã", "Valor Total" };

                for (int i = 0; i < cabecalhos.length; i++) {
                    Cell celula = linhaCabecalho.createCell(i);
                    celula.setCellValue(cabecalhos[i]);
                }

                int numeroLinha = 1;
                for (CompraPresencial compra : comprasPresenciais) {
                    Row linha = sheet.createRow(numeroLinha++);
                    linha.createCell(0).setCellValue(compra.getData());
                    linha.createCell(1).setCellValue(compra.getNomeVendedor());
                    linha.createCell(2).setCellValue(compra.getMatricula());
                    linha.createCell(3).setCellValue(compra.getQuantidadeTicketRefeicao());
                    linha.createCell(4).setCellValue(compra.getQuantidadeTicketCafeManha());
                    linha.createCell(5).setCellValue(compra.getValor().toString());
                }

                workbook.write(response.getOutputStream());
                response.getOutputStream().flush();
            }


        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public List<CompraPresencial> hitoricoComprasRealizadas(Long idUsuario, String matricula) {
        return compraPresencialRepository.comprasRealizadas(idUsuario,matricula);
    }

}
