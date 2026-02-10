package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.web.server.LocalServerPort.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        // Mengarahkan base URL ke halaman daftar produk
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void createProduct_isSuccessful(ChromeDriver driver) throws Exception {
        // 1. Buka halaman Create Product
        driver.get(baseUrl + "/product/create");

        // 2. Simulasi interaksi pengguna: Mengisi form
        String productName = "Sampo Cap Bambang";
        int productQuantity = 100;

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.clear();
        nameInput.sendKeys(productName);

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.clear();
        quantityInput.sendKeys(String.valueOf(productQuantity));

        // 3. Klik tombol submit untuk membuat produk
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // 4. Verifikasi: User dialihkan kembali ke halaman Product List
        String currentUrl = driver.getCurrentUrl();
        assertEquals(baseUrl + "/product/list", currentUrl);

        // 5. Verifikasi: Produk baru muncul di dalam tabel
        List<WebElement> productTableRows = driver.findElements(By.tagName("tr"));
        boolean isProductFound = false;

        for (WebElement row : productTableRows) {
            String rowText = row.getText();
            if (rowText.contains(productName) && rowText.contains(String.valueOf(productQuantity))) {
                isProductFound = true;
                break;
            }
        }

        assertTrue(isProductFound, "Produk yang baru dibuat tidak ditemukan di daftar produk.");
    }
}