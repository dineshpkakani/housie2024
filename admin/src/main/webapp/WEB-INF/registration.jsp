<html>
<head>
    <style>
        .regcontainer {
            max-width: 400px;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .regcontainer h2 {
            text-align: center;
        }
        .regcontainer input[type="text"],
        .regcontainer input[type="password"],
		.regcontainer input[type="date"],
        .regcontainer input[type="email"] {
            width: 100%;
            padding: 12px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            margin-bottom: 10px;
        }
        .regcontainer select {
            width: 100%;
            padding: 12px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            margin-bottom: 10px;
        }
        .regcontainer input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
        }
        .regcontainer input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>
</head>

<div class="regcontainer">
    <h2>Event Master</h2>
    <form action="/event/save" method="post">
        <label for="txteventname">Event Name</label>
        <input type="text" id="txteventname" name="txteventname" placeholder="Please enter eventname"/>

        <label for="txteventdate">Date</label>
        <input type="date" id="txteventdate" name="txteventdate" placeholder="please select eventdate"/>
		
		<label for="txtmaxtickets">Max Tickets</label>
        <input type="text" id="txtmaxtickets" name="txtmaxtickets" placeholder="Maximum tickets per employee"/>
		
		<label for="txtperprice">Price Per Ticket</label>
        <input type="text" id="txtperprice" name="txtperprice" placeholder="Maximum tickets per employee"/>
		
		<label for="txtperprice">Status</label>
        <select id="scltstatus" name="scltstatus">
			<option value="started">Started</option>
			<option value="Not started">Not Started</option>
			<option value="complted">Completed</option>
		</select>

        <input type="submit" value="Register">
    </form>
</div>
</div>
</body>
</html>