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
if (isset($_POST['groupType']) && isset($_POST['groupPrivacy']) && isset($_POST['groupName']) && isset($_POST['groupDescription']) && isset($_POST['pictureURL']) && isset($_POST['BoolImageChange'])) {
	$groupType = $_POST['groupType'];
	$groupPrivacy = $_POST['groupPrivacy'];
	$groupName = $_POST['groupName'];
	$groupDescription = $_POST['groupDescription'];
	$groupCreaterID=$_SESSION['userid'];
        $pictureURL= "/Images/" . $_POST['pictureURL'] . ".jpg";
        $BoolImageChange = $_POST['BoolImageChange'];
	// mysql inserting a new row
	if ($BoolImageChange == "true") {
    $result = mysql_query("INSERT INTO `group`(`groupCreaterID`, `groupType`, `groupPrivacy`, `groupName`, `groupDescription`, `pictureURL`) VALUES($groupCreaterID, '$groupType', '$groupPrivacy', '$groupName', '$groupDescription', '$pictureURL')");
    }
    else if ($BoolImageChange == "false") {
    $result = mysql_query("INSERT INTO `group`(`groupCreaterID`, `groupType`, `groupPrivacy`, `groupName`, `groupDescription`) VALUES($groupCreaterID, '$groupType', '$groupPrivacy', '$groupName', '$groupDescription')");
    }
	if ($result) {
		$joinResult = mysql_query("INSERT INTO `groupjoinin` (`userID`, `groupID`) VALUES ($userID, (SELECT `groupID` FROM `group` WHERE `groupCreaterID` = '$userID' AND `groupPrivacy` = '$groupPrivacy' AND `groupType` = '$groupType' AND `groupName` = '$groupName'))");
		 if($joinResult){
			// successfully inserted into database
			$response["success"] = 1;
			$response["message"] = "Create and join group succeed"; 
        // echoing JSON response
        echo json_encode($response);
		}
		 else{
        // failed to insert row
        $response["success"] = 0;
		$response["message"] = "Create and join group failed."; 
        // echoing JSON response
        echo json_encode($response); 
		 }
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