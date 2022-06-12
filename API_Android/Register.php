<?php
    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        include 'Connect.php';
        
        //read JSON from client
        // $json = file_get_contents('php://input', true);
        // $obj = json_decode($json);

        //get JSON object
        $fullname = $_POST['nama'];
        $no_telp = $_POST['no_ponsel'];
        $email = $_POST['email'];
        $password = $_POST['password'];

        $json_array = array();
        $response = "";

            $encryptedPassword = password_hash($password, PASSWORD_DEFAULT);
            $query_insert = "INSERT INTO daftar VALUES('', '$fullname',  '$email', '$no_telp', '$encryptedPassword')";
            if (mysqli_query($conn, $query_insert)) {
                $response = array(
                    'kode' => 201,
                    'pesan' => 'Daftar berhasil!'
                );
            } else {
                $response = array(
                    'kode' => 405,
                    'pesan' => 'Daftar gagal!'
                );
            }
        

        print(json_encode($response));
        mysqli_close($conn);
    }
?>