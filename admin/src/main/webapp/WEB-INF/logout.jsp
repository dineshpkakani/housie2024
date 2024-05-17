<!DOCTYPE html>
<html>
<head>
<title>User Homepage</title>
<style>
   body {
     font-family: Arial, sans-serif;
     background-color: #f2f2f2;
   }

   .container {
     max-width: 600px;
     margin: 0 auto;
     padding: 20px;
     background-color: #fff;
     border-radius: 5px;
     box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
   }

   .container h2 {
     text-align: center;
   }

   .user-info {
     margin-bottom: 20px;
   }

   .user-info label {
     font-weight: bold;
   }

   .user-info p {
     margin: 5px 0;
   }

   .logout-btn {
     text-align: center;
   }
</style>
</head>
<body>
<div class="container">
<h2>Welcome, John Doe!</h2>

<div class="user-info">
<label>Username:</label>
<p>johndoe</p>
<label>Email:</label>
<p>johndoe@example.com</p>
<label>Role:</label>
<p>Member</p>
</div>

<div class="logout-btn">
<a href="/logout">Logout</a>
</div>
</div>
</body>
</html>