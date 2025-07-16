import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Ganti nama database jadi toko_fina
        String jdbcURL = "jdbc:mysql://localhost:3306/toko_fina";
        String dbUser = "root";
        String dbPassword = ""; // Kosongkan kalau tidak pakai password

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Koneksi ke database
            Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
            System.out.println("✅ Koneksi ke database berhasil.");

            // Input dari user
            Scanner input = new Scanner(System.in);
            System.out.print("Masukkan kode barang: ");
            String kode = input.nextLine();
            System.out.print("Masukkan nama barang: ");
            String nama = input.nextLine();
            System.out.print("Masukkan harga barang: ");
            int harga = input.nextInt();
            System.out.print("Masukkan stok barang: ");
            int stok = input.nextInt();

            // Jalankan procedure insert_barang
            System.out.println("📌 Menjalankan prosedur insert...");
            CallableStatement cs = conn.prepareCall("{CALL insert_barang(?, ?, ?, ?)}");
            cs.setString(1, kode);
            cs.setString(2, nama);
            cs.setInt(3, harga);
            cs.setInt(4, stok);
            cs.execute();
            cs.close();
            System.out.println("✅ Data berhasil diinsert.");

            // Tampilkan data dari VIEW
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM view_barang_dengan_total");

            System.out.println("\n📦 Data Barang dari VIEW:");
            while (rs.next()) {
                System.out.println("Kode: " + rs.getString("kode") +
                        ", Nama: " + rs.getString("nama") +
                        ", Harga: " + rs.getInt("harga") +
                        ", Stok: " + rs.getInt("stok") +
                        ", Total Nilai: " + rs.getInt("total_nilai"));
            }

            // Tutup koneksi
            conn.close();

        } catch (Exception e) {
            System.out.println("❌ Terjadi kesalahan: " + e.getMessage());
        }
    }
}
