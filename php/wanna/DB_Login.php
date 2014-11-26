<?php
session_start();
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/include/DB_Connect.php';
 
// connecting to db
$db = new DB_Connect();

if (isset($_POST["loginEmail"])&& isset($_POST["loginPassword"])) {
	$loginEmail = $_POST['loginEmail'];
	$loginPassword = $_POST['loginPassword'];
	$result = mysql_query("select `userid`, `userType` from `users` where users.email='$loginEmail' and users.password=PASSWORD('$loginPassword')");
	if(!empty($result)){
		if (mysql_num_rows($result) > 0) {
			$result = mysql_fetch_array($result);
			$userType = $result['userType'];
			$userid = $result['userid'];
			if($userType == "Person"){
				$profileResult = mysql_query("select `profileID`, `nickName` from `personprofile` where `userid` = $userid");
			}else if($userType == "Organization"){
				$profileResult = mysql_query("select `profileID`, `nickName` from `organizationprofile` where `userid` = $userid");
			}
			if(!empty($profileResult)){
				if (mysql_num_rows($profileResult) > 0){
					$profileResult = mysql_fetch_array($profileResult);
					$_SESSION['userid'] = $result['userid'];
					$_SESSION['userType'] = $result['userType'];
					$_SESSION['profileid'] = $profileResult['profileID'];
					$_SESSION['nickName'] = $profileResult['nickName'];
					$sessionid=session_id();
					$_SESSION['$sessionid'] = $sessionid;
					$response["success"] = 1; 
					$response["message"] = "Login success";
					$response["sessionid"] = $sessionid;
					$response["userid"] = $_SESSION['userid'];
					$response["userType"] = $_SESSION['userType'];
					$response["profileid"] = $_SESSION['profileid'];
					$response["nickName"] = $_SESSION['nickName'];
					// echoing JSON response
					echo json_encode($response);
				}
				else{
					// required field is missing
					$response["success"] = 0;
					$response["message"] = "Invalid user info";
					// echoing JSON response
					echo json_encode($response);
				}				
			}else{
				// required field is missing
				$response["success"] = 0;
				$response["message"] = "get profile info failed";
				// echoing JSON response
				echo json_encode($response);
			}			
				
			$_SESSION['nickName'] = $result['nickName'];
			$_SESSION['profileid'] = $result['profileID'];
			$sessionid=session_id();
			$_SESSION['$sessionid'] = $sessionid;			
			// success
			$response["success"] = 1;
			$response["message"] = "Login success";
			$response["sessionid"] = $sessionid;
			$response["userid"] = $_SESSION['userid'];
			$response["nickName"] = $_SESSION['nickName'];
			$response["profileid"] = $_SESSION['profileid'];
			// echoing JSON response
            echo json_encode($response);
			}else{				
    // required field is missing
    $response["success"] = 0;
	$response["message"] = "Invalid user name or password";
 
    // echoing JSON response
    echo json_encode($response);
	}
}else{	
    // required field is missing
    $response["success"] = 0;
	$response["message"] = "get user info failed";
     // echoing JSON response
    echo json_encode($response);
}
}else{
    // required field is missing
    $response["success"] = 0;
	$response["message"] = "Pass data failed";
 
    // echoing JSON response
    echo json_encode($response);
}
?>