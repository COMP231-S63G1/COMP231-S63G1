<?php
if (isset($_FILES['myFile'])) {
    // Example:
	$file_path = "uploads/";

	$file_path = $file_path . basename( $_FILES['myFile']['name']);
    move_uploaded_file($_FILES['myFile']['tmp_name'], "uploads/" . $_FILES['myFile']['name']);
    
	 // include db connect class
    require_once __DIR__ . '/include/DB_Connect.php';
 
    // connecting to db
    $db = new DB_Connect();

    // mysql inserting a new row
		
  
    $result = mysql_query("INSERT INTO wanna.uploads (filename,path) VALUES ('".$_FILES['myFile']['tmp_name']."','".$_FILES['myFile']['name']."');");
    // check if row inserted or not
    echo 'successful';
}
?>
