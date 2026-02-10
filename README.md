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