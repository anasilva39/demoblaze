package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CompraMonitorSteps {
    WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Given("que estou na página inicial")
    public void abrirPaginaInicial() throws InterruptedException {
        driver.get("https://www.demoblaze.com");
        Thread.sleep(2000); // Pause de 2 segundos
    }

    @When("acesso a categoria de Monitores")
    public void acessarCategoriaMonitor() throws InterruptedException {
        driver.findElement(By.linkText("Monitors")).click();
        Thread.sleep(2000); // Pause de 2 segundos
    }

    @When("adiciono um monitor ao carrinho")
    public void adicionarMonitorAoCarrinho() {
        try {
            driver.findElement(By.xpath("//a[contains(text(),'Apple monitor 24')]")).click();
            Thread.sleep(2000); // Pause de 2 segundos
            driver.findElement(By.xpath("//a[contains(text(),'Add to cart')]")).click();
            Thread.sleep(2000); // Pause de 2 segundos

            // Lidar com o alerta
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            alert.accept(); // Aceita o alerta "Product added"
            Thread.sleep(2000); // Pause de 2 segundos após aceitar o alerta

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("vou para o carrinho")
    public void irParaCarrinho() throws InterruptedException {
        driver.findElement(By.id("cartur")).click();
        Thread.sleep(2000); // Pause de 2 segundos
    }

    @When("finalizo a compra preenchendo os dados")
    public void finalizarCompra() throws InterruptedException {
        driver.findElement(By.xpath("//button[contains(text(),'Place Order')]")).click();
        Thread.sleep(2000); // Pause de 2 segundos

        // Utilizando WebDriverWait para esperar elementos visíveis antes de interagir
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys("Ana Silva");
        driver.findElement(By.id("country")).sendKeys("Brasil Teste");
        driver.findElement(By.id("city")).sendKeys("Cidade Teste");
        driver.findElement(By.id("card")).sendKeys("1234 5678 9123 4567");
        driver.findElement(By.id("month")).sendKeys("11");
        driver.findElement(By.id("year")).sendKeys("2024");
        driver.findElement(By.xpath("//button[contains(text(),'Purchase')]")).click();
        Thread.sleep(2000); // Pause de 2 segundos após preencher os dados
    }

    @Then("a compra é realizada com sucesso")
    public void validarCompra() throws InterruptedException {
        WebElement mensagem = driver.findElement(By.className("sweet-alert"));
        Assertions.assertTrue(mensagem.isDisplayed(), "A mensagem de compra não foi exibida.");
        Thread.sleep(2000); // Pause de 2 segundos para ver a mensagem de sucesso
        driver.quit();  // Fechar o driver após a validação
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();  // Certifique-se de fechar o driver se ainda estiver aberto
        }
    }
}
