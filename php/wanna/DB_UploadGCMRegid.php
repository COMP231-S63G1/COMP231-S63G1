<?php
 
// array for JSON response
$response = array();

// include db connect class
    require_once __DIR__ . '/include/DB_Connect.php';
 
    // connecting to db
    $db = new DB_Connect();
 
require_once __DIR__ . '/DB_CheckLogin.php';
if($sessionSuccess == 1){
    $userID=$_SESSION['userid'];
 
// check for required fields
if (isset($_POST['gcmRegid'])) {
    $gcmRegid= $_POST['gcmRegid'];
    $result = mysql_query("UPDATE users SET gcm_regid='$gcmRegid' WHERE userid= $userID;");
    
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
		$response["message"] = "Upload gcm regid success.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
		$response["message"] = "Upload gcm regid failed.";
 
        // echoing JSON response
        echo json_encode($response);
    }


} else {
    // required field is missing
    $response["success"] = 0;
	$response["message"] = "Pass data failed.";
    // echoing JSON response
    echo json_encode($response);
}
}else{
		// failed
		$response["success"] = 0;
		$response["message"] = $sessionMessage;
		// echoing JSON response
		echo json_encode($response);	
} 
?>