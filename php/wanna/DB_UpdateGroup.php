<?php
 
// array for JSON response
$response = array();
 
// check for required fields 
if (isset($_POST['groupID']) && isset($_POST['groupType']) && isset($_POST['groupName']) && isset($_POST['groupDescription']) && isset($_POST['pictureURL']) && isset($_POST['BoolImageChange'])) {
	
    $groupID = $_POST['groupID'];
    $groupType = $_POST['groupType'];
    $groupName = $_POST['groupName'];
    $groupDescription = $_POST['groupDescription'];
        $pictureURL= "/Images/" . $_POST['pictureURL'] . ".jpg";
        $BoolImageChange = $_POST['BoolImageChange'];
 
    // include db connect class
    require_once __DIR__ . '/include/DB_Connect.php';
 
    // connecting to db
    $db = new DB_Connect();
 
    // mysql inserting a new row
    if ($BoolImageChange == "true") {
    $result = mysql_query("UPDATE `group` SET `groupType`='$groupType',`groupName`='$groupName',`groupDescription`='$groupDescription', `pictureURL` = '$pictureURL' WHERE `groupID`=$groupID;");
     }
    else if ($BoolImageChange == "false") {
    $result = mysql_query("UPDATE `group` SET `groupType`='$groupType',`groupName`='$groupName',`groupDescription`='$groupDescription' WHERE `groupID`=$groupID;");
     }
 
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "The group information is updated succeed";
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
		$response["message"] = "The group information updated failed";
        // echoing JSON response
        echo json_encode($response);
    }
		}else{
	// failed
	$response["success"] = 0;
	$response["message"] = "Pass search type failed";
	echo json_encode($response);
}
?>