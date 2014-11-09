<?php
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['userid']) &&isset($_POST['userNickName']) && isset($_POST['userGender']) && isset($_POST['userAge'])&& isset($_POST['userDescription'])) {
	$userid = $_POST['userid'];
    $userNickName = $_POST['userNickName'];
    $userGender = $_POST['userGender'];
    $userAge = $_POST['userAge'];
    $userDescription = $_POST['userDescription'];

    // include db connect class
    require_once __DIR__ . '/include/DB_Connect.php';
 
    // connecting to db
    $db = new DB_Connect();
 
    // mysql inserting a new row
    $result = mysql_query("INSERT INTO profile(nickName, gender, age, description, userid) VALUES('$userNickName', '$userGender', '$userAge', '$userDescription', '$userid')");
 
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
		$response["message"] = "Create profile succeed"; 
		session_start();
		$_SESSION['userid'] = $userid;
		$_SESSION['nickName'] = $userNickName;
		$sessionid=session_id();
		$_SESSION['$sessionid'] = $sessionid;
		// success
		$response["sessionid"] = $sessionid;
		$response["userid"] = $_SESSION['userid'];
		$response["nickName"] = $_SESSION['nickName'];
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
		$response["message"] = "Create profile failed."; 
 
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
