<?php
// array for JSON response
$response = array();
 
// include db connect class
//require_once __DIR__ . '/include/DB_Connect.php';
//$db = new DB_Connect();
require_once '/DB_CheckLogin.php';
if($sessionSuccess == 1){	
		// success
		$response["success"] = 1;
		$response["message"] = $sessionMessage;
		$response["userid"] = $_SESSION['userid'];
		$response["nickName"] = $_SESSION['nickName'];
		// echoing JSON response
		echo json_encode($response);
}else{
		// failed
		$response["success"] = 0;
		$response["message"] = $sessionMessage;
		// echoing JSON response
		echo json_encode($response);	
} 
?>