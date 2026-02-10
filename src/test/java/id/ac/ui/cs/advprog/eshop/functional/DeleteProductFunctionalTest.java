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
class DeleteProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        // Mengarahkan base URL ke root
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void deleteProduct_isSuccessful(ChromeDriver driver) throws Exception {
        // 1. Buat produk terlebih dahulu
        driver.get(baseUrl + "/product/create");
        driver.findElement(By.id("nameInput")).sendKeys("Produk Sampah");
        driver.findElement(By.id("quantityInput")).sendKeys("10");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // 2. Tunggu sebentar agar redirect selesai dan data muncul
        Thread.sleep(1000);

        // 3. Klik tombol Delete
        // Strategi baru: Cari baris yang mengandung 'Produk Sampah', lalu cari tombol di dalam tag <form>
        WebElement deleteButton = driver.findElement(By.xpath("//td[contains(text(), 'Produk Sampah')]/..//form/button"));
        deleteButton.click();

        // 4. Beri waktu proses penghapusan
        Thread.sleep(1000);

        // 5. Verifikasi produk sudah tidak ada
        List<WebElement> productCells = driver.findElements(By.xpath("//td[contains(text(), 'Produk Sampah')]"));
        assertTrue(productCells.isEmpty(), "Produk seharusnya sudah terhapus dari daftar.");
    }
}