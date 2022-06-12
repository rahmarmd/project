<?php 
require_once 'Connect.php';

if ( $conn ) {
    $email = $_POST['email'];
    $password = $_POST['password'];

    $query = "SELECT * FROM daftar WHERE Email='$email' ";
    // $query = "SELECT * FROM users WHERE email='$email' AND password='$password' ";
    $result = mysqli_query($conn, $query);
    // $response = array();

    // $row = mysqli_num_rows($result);
    // $rows = mysqli_fetch_array($result);
    if ( mysqli_num_rows($result) > 0 ) {
        $rows = mysqli_fetch_assoc($result);
        // $response['pesan'] = "BERHASIL";
        // $response['data'] = array();

        // while ($ambil = mysqli_fetch_object($result)){
        //     $F["id_user"] = $ambil->id_user;
        //     $F["fullname"] = $ambil->fullname;
        //     $F["username"] = $ambil->username;
        //     $F["email"] = $ambil->email;
        //     $F["no_telp"] = $ambil->no_telp;
        //     $F["foto_profil"] = $ambil->foto_profil;
            
        //     array_push($response['data'], $F);
        // }
        
            // $rows = mysqli_fetch_assoc($result);
        if ( password_verify($password, $rows["Kata_sandi"]) ) {
            $pesan = "BERHASIL";
           $id_user = $rows['id'];
           $fullname = $rows['Nama'];
           $email = $rows['Email'];
           $no_telp = $rows['Nomor_ponsel'];

        $response= array('pesan'=>$pesan,'id' => $id_user, 'nama' => $fullname, 'email' => $email, 'nomor_ponsel' => $no_telp);
        //     $response['pesan'] = $pesan;
        //   $response['datauser'] = array('id_user' => $id_user, 'fullname' => $fullname, 'username' => $username, 'email' => $email, 'no_telp' => $no_telp, 'foto_profil' => $foto_profil);
      
        } else {
            $response['pesan'] = "WRONG";
        }
        // $temp = array();
	
        // $temp['id_user'] = $id_user;
        // $temp['fullname'] = $fullname;
        // $temp['username'] = $username;
        // $temp['email'] = $email;
        //     array_push($response, $temp);
        
    } else {
        $response['pesan'] = "NOT FOUND";
    }
} else {
    $response['pesan'] = "FAILED";
}

echo json_encode($response);
mysqli_close($conn);
?>