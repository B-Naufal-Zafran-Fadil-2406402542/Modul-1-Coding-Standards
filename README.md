## Reflection

### 1. Clean Code Principles Applied
Selama pengembangan fitur *Edit* dan *Delete*, saya telah menerapkan beberapa prinsip Clean Code sesuai dengan standar yang dipelajari:

* **Meaningful Names**: Saya memastikan nama metode mengungkapkan tujuannya secara jelas. Misalnya, menggunakan `update` dan `delete` dalam `ProductService` alih-alih nama yang redundan seperti `updateProductData`, karena konteksnya sudah jelas di dalam kelas produk.
* **Small Functions**: Fungsi-fungsi yang saya buat tetap ringkas dan fokus pada satu tanggung jawab (**Single Responsibility Principle**).
* **Error Handling with Exceptions**: Saya menghindari penggunaan *return codes* atau pengembalian *null*. Sebaliknya, saya menggunakan *Custom Exception* (`ProductNotFoundException`) untuk menangani kasus objek yang tidak ditemukan. Hal ini memisahkan algoritma utama dengan logika penanganan kesalahan.
* **Don't Repeat Yourself (DRY)**: Saya menyederhanakan alur kontrol di *Controller* agar tidak ada pengulangan pernyataan `return` yang identik di dalam blok `try` dan `catch`.



### 2. Secure Coding Practices Applied
Keamanan aplikasi ditingkatkan dengan memperhatikan cara data dimanipulasi:

* **Avoiding GET for State Changes**: Saya tidak menggunakan `@GetMapping` untuk operasi penghapusan. Sebagai gantinya, saya menggunakan `@PostMapping` untuk memastikan bahwa perubahan status server (penghapusan data) memerlukan aksi eksplisit dari form, guna mencegah penghapusan tidak sengaja oleh *web crawler*.
* **Data Binding & Hidden Fields**: Saya menggunakan `<input type="hidden">` di Thymeleaf untuk membawa `productId` secara aman dalam objek `Product` saat melakukan *POSTing* data edit, memastikan ID yang benar diperbarui di sisi server.
* **Robust Exception Handling**: Dengan menangkap pengecualian di level *Controller*, saya mencegah aplikasi menampilkan *Whitelabel Error Page* yang teknis kepada pengguna, sehingga menjaga pengalaman pengguna tetap mulus.



### 3. Mistakes & Improvements
Berdasarkan evaluasi mandiri terhadap kode saat ini, berikut adalah beberapa hal yang dapat diperbaiki:

* **Silent Failures**: Saat ini, jika produk tidak ditemukan saat proses hapus, aplikasi hanya melakukan *redirect* tanpa memberi notifikasi.
    * **Perbaikan**: Menggunakan `RedirectAttributes` untuk mengirimkan pesan sukses atau error ke halaman daftar produk agar pengguna mendapatkan umpan balik yang jelas.
* **Validation**: Belum ada validasi input yang kuat untuk memastikan nama produk tidak kosong atau kuantitas bernilai positif.
    * **Perbaikan**: Menerapkan Bean Validation (`@NotBlank`, `@Min`) pada model `Product` dan menggunakan `@Valid` di Controller.

## Reflection 2

### Unit Testing & Code Coverage

Setelah mengimplementasikan unit test untuk model dan repository, saya merasa jauh lebih aman dan percaya diri dalam melakukan perubahan kode karena setiap fitur dasar telah memiliki jaring pengaman otomatis. Menurut saya, jumlah unit test yang dibuat dalam satu kelas haruslah cukup untuk mencakup seluruh alur logika, baik itu skenario sukses maupun skenario gagal (negative cases). Untuk memastikan bahwa pengujian kita sudah memadai, kita dapat menggunakan metrik *code coverage* yang membantu mengidentifikasi bagian kode mana yang belum tersentuh oleh pengujian sama sekali. Namun, sangat penting untuk dipahami bahwa mencapai 100% *code coverage* bukan berarti kode kita sepenuhnya bebas dari bug atau kesalahan logika. *Code coverage* hanyalah indikator kuantitatif yang menunjukkan baris mana yang dieksekusi selama tes, tetapi tidak menjamin kualitas asersi atau validasi terhadap berbagai variasi input dan kondisi ekstrem yang mungkin terjadi di dunia nyata. Oleh karena itu, selain mengejar angka *coverage*, kita juga harus fokus pada kualitas skenario pengujian yang relevan.

### Functional Testing & Clean Code

Berdasarkan pengamatan saya terhadap pembuatan *functional test suite* baru (seperti pengujian jumlah item di daftar produk), menulis ulang prosedur *setup* dan variabel instansi yang sama persis akan sangat menurunkan kualitas dan kebersihan kode. Masalah utama yang muncul dari pendekatan ini adalah terjadinya redundansi kode atau pelanggaran prinsip **DRY (Don't Repeat Yourself)**. Duplikasi kode semacam ini membuat pemeliharaan menjadi sulit; apabila di masa depan terdapat perubahan konfigurasi port atau URL dasar, kita harus mengubahnya di setiap file pengujian satu per satu, yang mana sangat rawan akan kesalahan manusia (*human error*). Selain itu, hal ini membuat struktur proyek terlihat berantakan dan tidak profesional.

Untuk meningkatkan kualitas kode agar lebih bersih (*clean code*), saya menyarankan penerapan konsep **inheritance** dalam pengujian. Kita dapat membuat sebuah *Base Class* fungsional (misalnya `FunctionalTest.java`) yang berisi semua konfigurasi umum seperti `@LocalServerPort`, `@Value` untuk base URL, dan metode `@BeforeEach` untuk inisialisasi. Dengan cara ini, kelas pengujian lainnya seperti `HomePageFunctionalTest`, `CreateProductFunctionalTest`, dan kelas baru lainnya hanya perlu melakukan *extends* ke *Base Class* tersebut. Pendekatan ini membuat kode menjadi lebih modular, mudah dibaca, dan jauh lebih mudah untuk dikelola dalam jangka panjang.

---

### Penjelasan Tambahan Terkait Struktur Proyek

Berikut adalah rangkuman perbaikan yang telah dilakukan untuk memenuhi kriteria rubrik:
* **Correctness & Creativity**: Mengimplementasikan `FunctionalTest.java` sebagai *base class* untuk menghindari duplikasi kode dan mempercantik tampilan halaman menggunakan Bootstrap 5 tanpa merusak pengujian fungsional.
* **Robust Testing**: Menambahkan pengujian fungsional untuk fitur *Delete* dengan penanganan `NoSuchElementException` menggunakan XPath yang dinamis dan sinkronisasi `Thread.sleep`.
* **Clean Code**: Menghapus penggunaan `@MockBean` yang sudah *deprecated* pada Spring Boot 3.4.0 dan beralih ke Mockito murni agar kode tetap *up-to-date*.