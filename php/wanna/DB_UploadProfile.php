<?php
 
// array for JSON response
$response = array();
 
// check for required fields 
if (isset($_POST['sessionID']) &&isset($_POST['userID']) && isset($_POST['userNickName']) && isset($_POST['userDescription'])) {
 //   $profileID = $_POST['profileID'];
    $userNickName = $_POST['userNickName'];
    $userDescription = $_POST['userDescription'];
 
  //  echo( $userNickName);
	//echo($userDescription );
    // include db connect class
    require_once __DIR__ . '/include/DB_Connect.php';
 
    // connecting to db
    $db = new DB_Connect();
require_once '/DB_CheckLogin.php';
if($sessionSuccess == 1){
	$userID=$_SESSION['userid'];
    // mysql inserting a new row
    $result = mysql_query("UPDATE profile SET nickName = '$userNickName', description = '$userDescription' WHERE userid = $userID;");
 
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database	
		//update nick name in session	
		$_SESSION['nickName'] = $userNickName;
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
		$response["message"] = $sessionMessage;
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
?>