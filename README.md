# 💳 Capiwallet

Sistema desenvolvido como Trabalho de Conclusão de Curso (TCC) por **Pedro Nunes Cardoso** e **Lennon Ferreira Machado**, intitulado:

**Capiwallet: Um Sistema Web de Carteira Digital para o Restaurante Universitário da UFRRJ**.

O projeto foi desenvolvido utilizando **Java 21**, **Spring Boot 3.3.2** e **MySQL 8.0.28**.


---

## 🎯 Propósito

Modernizar e automatizar o processo de compra e utilização de tickets no restaurante universitário, por meio de uma **carteira digital integrada**. O sistema visa:

- Facilitar o uso por parte dos estudantes;
- Agilizar o controle de acesso ao restaurante;
- Otimizar o gerenciamento interno pela equipe administrativa.

---

## 📅 Metodologia de Desenvolvimento

O desenvolvimento seguiu uma abordagem incremental, dividido nas seguintes etapas principais:

1. Levantamento de requisitos e estudo de viabilidade;
2. Escolha da arquitetura e definição de tecnologias;
3. Desenvolvimento iterativo com testes manuais e automatizados;
4. Validação com usuários simulados;
5. Documentação e entrega final.

---
## 🛠️ Tecnologias Utilizadas

<div style="display: flex; gap: 10px;">
  
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)
![ZXing](https://img.shields.io/badge/ZXing-000000?style=for-the-badge&logo=qr-code&logoColor=white)
![iText PDF](https://img.shields.io/badge/iText-PDF-FF6F61?style=for-the-badge&logo=adobe&logoColor=white)
![Apache POI](https://img.shields.io/badge/Apache_POI-6DB33F?style=for-the-badge&logo=apache&logoColor=white)

</div>

---
## 📦 Funcionalidades

- [x] Cadastro e login de usuários
- [x] Edição de dados de perfil (nome, e-mail, senha e foto)
- [x] Recuperação de senha por e-mail com link de alteração
- [x] Disparo automático de e-mails informativos (compra de tickets, saldo baixo, etc.)
- [x] Compra de tickets de forma presencial ou digital
- [x] Integração com a **API Pix do Banco do Brasil** para pagamentos
- [x] Visualização de saldo de tickets e histórico de uso
- [x] Geração de **QR Codes** para autenticação no restaurante
- [x] Painel administrativo com gestão de vendas e permissões
- [x] Geração de relatórios diários em formato PDF e Excel para controle administrativo
- [x] Acompanhamento de atividades do sistema e dos usuários
- [x] Interface responsiva e intuitiva para acesso via celular e desktop
- [x] Alertas visuais e por e-mail sobre status de tickets e ações importantes

---

## 🧱 Arquitetura

- Foi realizado um estudo comparativo entre arquiteturas de **microsserviços** e **monolítica**.
- Optou-se pela arquitetura **monolítica** devido ao tamanho do projeto, facilidade de desenvolvimento, menor complexidade na implantação e manutenção, além de atender adequadamente às necessidades do sistema.
- Camadas bem definidas:
  - `Controller`
  - `Service`
  - `Repository`
  - `Model`
- Utilização de **Spring Data JPA** para abstração do acesso ao banco de dados.

---

## 🔐 Autenticação e Segurança

- Autenticação baseada em **HttpSession**
- Controle de acesso por **roles (usuario, administrador, tecnico)**
- Redirecionamentos e filtros com base nas permissões
- Proteção contra acesso não autorizado em páginas críticas

---

## 🧪 Testes

- Foram utilizadas as seguintes bibliotecas para a implementação dos testes unitários:

    - JUnit Jupiter (5.10.3): framework de testes moderno que oferece anotações e funcionalidades poderosas para escrita de testes claros e organizados.

    - Mockito Core (5.11.0): biblioteca de mocking utilizada para simular comportamentos de dependências e testar componentes isoladamente.

---

## 🖼️ Imagens do Sistema

### 🔑 Tela de Login  
Permite que o usuário acesse o sistema utilizando matrícula e senha cadastrados.

<img width="1085" height="689" alt="Image" src="https://github.com/user-attachments/assets/a9c75f6d-d0bb-4214-afcc-d5aaf1649a36" />


### 👛 Página da Carteira  
Exibe o saldo atual de tickets e com opção para compra de novos tickets.

<img width="1085" height="689" alt="Image" src="https://github.com/user-attachments/assets/615fb395-9ff4-44c0-b7de-e58b0de792b9" />


### 👤 Página do Usuário  
Mostra as informações pessoais do usuário e permite edição de dados como e-mail, senha e imagem de perfil.

<img width="1085" height="689" alt="Image" src="https://github.com/user-attachments/assets/93991348-68da-4305-a64e-90819c6d45f5" />


### 🔑 Página do QRCode  
Gera um QR Code único por usuário para autenticação no restaurante universitário.

<img width="1085" height="689" alt="Image" src="https://github.com/user-attachments/assets/010fe980-a781-47c9-bb62-cda05bf4e272" />

### 📷 Leitura de QR Code

Página responsável por identificar o usuário por meio de sua matrícula previamente cadastrada.

<img width="1085" height="689" alt="Leitura de QR Code" src="https://github.com/user-attachments/assets/7b7d0d84-231c-4abd-816c-be41b96784d0" />



---

## 📐 Diagramas

### 📌 Diagrama de Casos de Uso  
Representa os principais atores (usuário, administrador, técnico) e os casos de uso relacionados ao sistema.
<img width="1500" height="1680" alt="Image" src="https://github.com/user-attachments/assets/b0a12617-9643-48fc-891e-711fff1583ff" />


### 🧩 Diagrama de Classes (UML)
Mostra a estrutura das classes, atributos e relacionamentos no backend do sistema.
<img width="946" height="1266" alt="Image" src="https://github.com/user-attachments/assets/68e6ac48-b5d3-4015-86b8-cc6faf539e48" />

---

## 📥 Como Executar Localmente

Requer Java 21 e MySQL 8.0+

1. Clone o repositório:
```bash
git clone https://github.com/uLennon/Capiwallet.git
```
2. Configure o banco de dados MySQL local com o schema, user e password;

3. Renomeie o application-example.yml para application.yml e substitua os valores de exemplo pelas suas credenciais reais;

4. Execute o projeto com o Maven:
```bash
./mvnw spring-boot:run
```

## 👨‍💻 Autores

- Lennon Ferreira Machado  
- Pedro Nunes Cardoso 
