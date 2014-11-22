<?php
 
// array for JSON response
$response = array();

// include db connect class
	require_once __DIR__ . '/include/DB_Connect.php';
 
	// connecting to db
	$db = new DB_Connect();
 
require_once '/DB_CheckLogin.php';
if($sessionSuccess == 1){
	$userID=$_SESSION['userid'];
 
// check for required fields
if (isset($_POST['groupType']) && isset($_POST['groupPrivacy']) && isset($_POST['groupName']) && isset($_POST['groupDescription'])) {
	$groupType = $_POST['groupType'];
	$groupPrivacy = $_POST['groupPrivacy'];
	$groupName = $_POST['groupName'];
	$groupDescription = $_POST['groupDescription'];
	$groupCreaterID=$_SESSION['profileid'];
	// mysql inserting a new row
    $result = mysql_query("INSERT INTO `group`(`groupCreaterID`, `groupType`, `groupPrivacy`, `groupName`, `groupDescription`) VALUES($groupCreaterID, '$groupType', '$groupPrivacy', '$groupName', '$groupDescription')");
	if ($result) {
			// successfully inserted into database
			$response["success"] = 1;
			$response["message"] = "Create profile succeed"; 
        // echoing JSON response
        echo json_encode($response);
		
	}else{
        // failed to insert row
        $response["success"] = 0;
		$response["message"] = "Create group failed."; 
        // echoing JSON response
        echo json_encode($response); 
	}
	}
	else{
	// required field is missing
	$response["success"] = 0;
	$response["message"] = "Pass data failed.";  
	// echoing JSON response
	echo json_encode($response);
	}
}
	else{
		// failed
		$response["success"] = 0;
		$response["message"] = $sessionMessage;
		// echoing JSON response
		echo json_encode($response);	
} 
?>