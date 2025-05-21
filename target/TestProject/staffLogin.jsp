<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>Staff Login</title>
  <link rel="stylesheet" href="styles.css" />
</head>
<body>
<div class="login-container">
  <form class="login-form" action="staffLogin" method="post">
    <h2>Staff Login</h2>


    <div class="input-group">
      <label for="email">Email:</label>
      <input type="email" id="email" name="email" required />
    </div>

    <div class="input-group password-container">
      <label for="password">Password:</label>
      <input type="password" id="password" name="password" required />
    </div>

    <button type="submit" class="btn-primary">Login as Staff</button>
  </form>

  <br/>

  <!-- Back button centered at the bottom -->
  <div style="position: fixed; bottom: 20px; left: 50%; transform: translateX(-50%);">
    <form action="index.jsp" method="get">
      <input type="submit" value="Back to Home"
             style="font-weight:bold; font-size:16px; padding:8px 16px; cursor:pointer;">
    </form>
  </div>
</div>
</body>
</html>
