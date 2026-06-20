# SauceDemo UI Automation — Selenium

Automação de testes E2E com **Java + Maven + TestNG + Selenium 4** para o site [https://www.saucedemo.com](https://www.saucedemo.com).

<img width="894" height="379" alt="image" src="https://github.com/user-attachments/assets/d09c7673-eeff-4e89-8cfd-24d5399ec9cd" />

<img width="1887" height="891" alt="image" src="https://github.com/user-attachments/assets/e5acc957-79e3-44fc-a0f2-a7ecfab2dbd8" />

---

## Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 21 |
| Maven | 3.9+ |
| Selenium | 4.27.0 |
| TestNG | 7.10.2 |
| Allure | 2.27.0 |
| OpenPDF | 1.3.35 |

---

## Pré-requisitos

- **Java 21** instalado e configurado no `JAVA_HOME`
- **Maven 3.9+** instalado e disponível no PATH
- **Google Chrome** instalado (o Selenium Manager baixa o ChromeDriver automaticamente)
- **Allure CLI** instalado para geração de relatórios

### Instalando o Allure CLI

**Windows (via Scoop):**
```bash
scoop install allure
```

**Windows (via Chocolatey):**
```bash
choco install allure
```

**Verificar instalação:**
```bash
allure --version
```

---

## Instalação

Clone o repositório e instale as dependências:

```bash
git clone <url-do-repositorio>
cd saucedemoUIAutomationSelenium
mvn install -DskipTests
```

---

## Estrutura do Projeto

```
src/
├── main/java/
│   ├── evidence/
│   │   ├── EvidenceHolder.java          # Armazena os passos capturados por thread
│   │   ├── EvidencePdfGenerator.java    # Gera o PDF com prints por ação
│   │   └── ScreenshotEventListener.java # Captura screenshot após cada ação Selenium
│   └── pages/
│       ├── LoginPage.java
│       ├── HomePage.java
│       ├── CartPage.java
│       ├── CheckoutPage.java
│       └── ProductPage.java
└── test/java/
    ├── base/
    │   └── BaseTest.java                # Configura e encerra o WebDriver
    ├── listeners/
    │   └── EvidenceListener.java        # TestNG listener que dispara a geração de PDF
    └── tests/
        ├── login/LoginTest.java
        ├── home/HomeTest.java
        ├── cart/CartTest.java
        └── cards/CardsTest.java
```

---

## Cenários de Teste

### Login (7 cenários)
- Validar que site abriu com sucesso
- Validar login com sucesso
- Validar login com falha
- Validar login com campos vazios
- Validar login com usuário bloqueado
- Validar logout
- Validar abrir navegador

### Home (9 cenários)
- Validar que a home carregou com sucesso
- Validar que os produtos são exibidos
- Validar nome do primeiro produto
- Validar preço do primeiro produto
- Validar imagem do primeiro produto
- Validar adicionar produto ao carrinho
- Validar remover produto do carrinho
- Validar ordenação por nome (A-Z)
- Validar ordenação por preço (menor para maior)

### Cards / Produto (7 cenários)
- Validar detalhes do produto
- Validar nome do produto na página de detalhes
- Validar preço do produto na página de detalhes
- Validar imagem do produto na página de detalhes
- Validar botão adicionar ao carrinho na página de detalhes
- Validar botão voltar à home
- Validar link "About" no menu

### Cart / Checkout (5 cenários)
- Validar carrinho
- Validar checkout sem informação
- Validar compra completa de produto
- Validar remover pedido do carrinho
- Validar cancelar compra de produto

---

## Executando os Testes

### Rodar toda a suite

```bash
mvn test
```

Os testes rodam em **paralelo** (4 threads), cada suite em sua própria thread com seu próprio WebDriver.

### Rodar uma classe específica

```bash
mvn test -Dtest=LoginTest
mvn test -Dtest=HomeTest
mvn test -Dtest=CartTest
mvn test -Dtest=CardsTest
```

---

## Evidências em PDF

A cada execução, é gerado automaticamente um **arquivo PDF por teste** dentro da pasta `evidence/` na raiz do projeto.

### Como funciona

1. O `ScreenshotEventListener` intercepta cada ação do Selenium (cliques, digitações, navegações)
2. Após cada ação, captura um screenshot e registra a descrição do elemento
3. Ao final de cada teste, o `EvidenceListener` gera um PDF com todos os passos

### Estrutura do PDF

Cada PDF contém:
- Nome do teste e status (**PASSED** em verde / **FAILED** em vermelho)
- Data e hora da execução
- Quantidade de passos
- Para cada passo: descrição da ação + screenshot + horário da captura

### Localização dos arquivos

```
evidence/
├── LoginTest_validarLoginComSucesso.pdf
├── LoginTest_validarLoginComFalha.pdf
├── HomeTest_validarProdutosExibidos.pdf
└── ...
```

> A pasta `evidence/` está no `.gitignore` e não é versionada.

---

## Allure Report

### Gerar e abrir o relatório

Após rodar `mvn test`, execute:

```bash
# Gera e abre o relatório no navegador
mvn allure:serve
```

O comando acima sobe um servidor local e abre o Allure automaticamente no browser.

### Gerar relatório estático (sem servidor)

```bash
# Gera os arquivos HTML em target/site/allure-maven-plugin/
mvn allure:report
```

Para visualizar, abra o arquivo gerado em:
```
target/site/allure-maven-plugin/index.html
```

### Fluxo completo (rodar testes + abrir relatório)

```bash
mvn test && mvn allure:serve
```

> Os resultados brutos ficam em `target/allure-results/`. Esta pasta é recriada a cada `mvn test`.

---

## Paralelismo

A execução paralela é configurada no `testng.xml`:

```xml
<suite parallel="tests" thread-count="4">
```

Cada `<test>` (Login, Home, Cart, Cards) roda em uma thread separada com seu próprio `WebDriver`, garantindo isolamento total entre as suites.

---

## .gitignore

Os seguintes artefatos são ignorados pelo git:

```
target/          # build Maven
.allure/         # binários do Allure CLI baixados localmente
allure-results/  # resultados brutos de execução
allure-report/   # relatório gerado
evidence/        # PDFs de evidência gerados nos testes
*.pdf            # qualquer PDF avulso
```
