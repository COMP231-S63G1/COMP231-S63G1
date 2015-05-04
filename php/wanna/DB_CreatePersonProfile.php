<?php
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['userid']) && isset($_POST['userType']) && isset($_POST['nickName']) && isset($_POST['userGender']) && isset($_POST['userAge']) && isset($_POST['userDescription']) && isset($_POST['pictureURL']) && isset($_POST['BoolImageChange'])) {
	$userid = $_POST['userid'];
	$userType = $_POST['userType'];
    $nickName = $_POST['nickName'];
    $userGender = $_POST['userGender'];
    $userAge = $_POST['userAge'];
    $userDescription = $_POST['userDescription'];
        $pictureURL= "/Images/" . $_POST['pictureURL'] . ".jpg";
        $BoolImageChange = $_POST['BoolImageChange'];

    // include db connect class
    require_once __DIR__ . '/include/DB_Connect.php';
 
    // connecting to db
    $db = new DB_Connect();
    
    if ($BoolImageChange == "true") {
    // mysql inserting a new row
    $result = mysql_query("INSERT INTO `personprofile`(`nickName`, `gender`, `age`, `description`, `userid`, `pictureURL`) VALUES('$nickName', '$userGender', '$userAge', '$userDescription', '$userid', '$pictureURL')");
    }
    else if ($BoolImageChange == "false") {
    // mysql inserting a new row
    $result = mysql_query("INSERT INTO `personprofile`(`nickName`, `gender`, `age`, `description`, `userid`) VALUES('$nickName', '$userGender', '$userAge', '$userDescription', '$userid')");
    }
 
    // check if row inserted or not
    if ($result) {
		$selectResult = mysql_query("SELECT `profileID` FROM `personprofile` WHERE `userid` ='$userid'");
		if(!empty($selectResult)){
		if (mysql_num_rows($selectResult) > 0) {
			// successfully inserted into database
			$response["success"] = 1;
			$response["message"] = "Create profile succeed"; 
			session_start();
			$_SESSION['userid'] = $userid;
			$_SESSION['userType'] = $userType;
			$_SESSION['nickName'] = $nickName;
			$sessionid=session_id();
			$_SESSION['$sessionid'] = $sessionid;
			// success
			$response["sessionid"] = $sessionid;
			$response["userid"] = $_SESSION['userid'];
			$response["nickName"] = $_SESSION['nickName'];
			// echoing JSON response
			echo json_encode($response);
			}else{
				// required field is missing
				$response["success"] = 0;
				$response["message"] = "Create user profile failed.";
				// echoing JSON response
				echo json_encode($response);
				}
				}
				else{	
				// required field is missing
				$response["success"] = 0;
				$response["message"] = "Database connection failed";
				// echoing JSON response
				echo json_encode($response);
				}
				}
				else {
        // failed to insert row
        $response["success"] = 1;
		$response["message"] = "Create profile success.";  
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
?>