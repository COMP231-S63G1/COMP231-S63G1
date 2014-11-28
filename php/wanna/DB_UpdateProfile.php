<?php
 
// array for JSON response
$response = array();
require_once __DIR__ . '/include/DB_Connect.php';
 
// connecting to db
$db = new DB_Connect();
require_once '/DB_CheckLogin.php';
if($sessionSuccess == 1){ 
// check for required fields 
if (isset($_POST['nickName']) && isset($_POST['description'])) {

    $nickName = $_POST['nickName'];
    $description = $_POST['description'];


	$userID=$_SESSION['userid'];
	$userType=$_SESSION['userType'];
	if($userType == "Person"){
    // mysql inserting a new row
    $result = mysql_query("UPDATE `personprofile` SET `nickName` = '$nickName', `description` = '$description' WHERE `userid` = $userID;");		
	}
	else if($userType == "Organization"){
    // mysql inserting a new row
    $result = mysql_query("UPDATE `organizationprofile` SET `nickName` = '$nickName', `description` = '$description' WHERE `userid` = $userID;");
	} 
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database	
		//update nick name in session	
		$_SESSION['nickName'] = $nickName;
        $response["success"] = 1;
		$response["message"] = "Updata user profile success";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
		$response["message"] = "Updata user profile failed";
 
        // echoing JSON response
        echo json_encode($response);
    }
}
else{
		// failed
		$response["success"] = 0;
		$response["message"] = "Pass data failed.";
		// echoing JSON response
		echo json_encode($response);
}
}
else{
    // required field is missing
    $response["success"] = 0;
	$response["message"] = $sessionMessage;
 
    // echoing JSON response
    echo json_encode($response);
}
?>