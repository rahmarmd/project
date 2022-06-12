<?php 
if ($_SERVER["REQUEST_METHOD"] == 'POST') {
  require "Connect.php";
  if ($conn) {

    $encoded_file = $_POST["Bukti_pembayaran"];
    $id_daftar = $_POST["id_daftar"];
    $Nama = $_POST["Nama"];
    $Email = $_POST["Email"];
    $Nomor_ponsel = $_POST["Nomor_ponsel"];
    $Alamat = $_POST["Alamat"];
    $Tanggal_pemesanan = date('Y-m-d');
    $Jenis_pemesanan = $_POST["Jenis_pemesanan"];
    $Deskripsi_pemesanan = $_POST["Deskripsi_pemesanan"];
    $id_kategori= $_POST["id_kategori"];
    // $id_daftar = $_POST["Bukti_pembayaran"];

    $filename = uniqid().".jpeg";

    // try{

    //     $queryUPDATE = mysqli_query($conn, "INSERT INTO form ('id_daftar',  'Email') VALUES ('', $id_daftar, $Nama, $Email, $Nomor_ponsel, $Alamat, '', $Jenis_pemesanan, $Deskripsi_pemesanan, $id_kategori, $filename)");
    //     if (mysqli_affected_rows($conn) > 0) {
    //         file_put_contents("images/pembayaran/".$filename, base64_decode($encoded_file));
            
    //         $response = array('pesan' => 'BERHASIL');
    //     } 
    // } catch (Exception $e) {
    //     $response = array('pesan' => $e);
    // }

    // $queryUPDATE = mysqli_query($conn, "INSERT INTO form ('id_daftar', 'Nama', 'Email', 'Nomor_ponsel', 'Alamat', 'Jenis_pemesanan', 'Deskripsi_pemesanan', 'id_kategori', 'Bukti_pembayaran') VALUES ('$id_daftar', '$Nama', '$Email', '$Nomor_ponsel', '$Alamat', '$Jenis_pemesanan', '$Deskripsi_pemesanan', '$id_kategori', 'test')");
    // $queryUPDATE = mysqli_query($conn, "INSERT INTO form ('id_daftar', 'Nama', 'Email', 'Nomor_ponsel', 'Alamat', 'Jenis_pemesanan', 'Deskripsi_pemesanan', 'id_kategori', 'Bukti_pembayaran') VALUES ('3', 'yoga', 'yoga@gmail.com', '085647635463', 'jombang', 'standar', 'premium', '1', 'test')");
    $queryUPDATE = mysqli_query($conn, "INSERT INTO form VALUES ('', '$id_daftar', '$Nama', '$Email', '$Nomor_ponsel', '$Alamat', '$Tanggal_pemesanan', '$Jenis_pemesanan', '$Deskripsi_pemesanan', '$id_kategori', '$filename')");

    if ($queryUPDATE) {
      file_put_contents("images/pembayaran/".$filename, base64_decode($encoded_file));

      $response = array('pesan' => 'BERHASIL');
    } else {
      $response = array('pesan' => 'GAGAL');
    } 

  } else {
    $response = array('pesan' => 'NOT CONNECTED');
  }
  mysqli_close($conn);
  echo json_encode($response);
}
?>