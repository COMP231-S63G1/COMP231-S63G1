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
	$result = mysql_query("select users.userid, profile.profileID, profile.nickName from users, profile where users.email='$loginEmail' and users.password=PASSWORD('$loginPassword') and users.userid=profile.userid limit 1");
	if(!empty($result)){
		if (mysql_num_rows($result) > 0) {
			$result = mysql_fetch_array($result);	
			$_SESSION['userid'] = $result['userid'];
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
	$response["message"] = "Database connection failed";
 
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