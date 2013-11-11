<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>NMSWebApp</title>
        <style> <%@include file="/core/css/style.css" %> </style>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
    </head>
    <body>
        <div id="page-background">
            <div id="page-background-image"> </div>
        </div>
    <div id="main">
        <div class="sheet">
            <div class="sheet-tl"></div>
            <div class="sheet-tr"></div>
            <div class="sheet-bl"></div>
            <div class="sheet-br"></div>
            <div class="sheet-tc"></div>
            <div class="sheet-bc"></div>
            <div class="sheet-cl"></div>
            <div class="sheet-cr"></div>
            <div class="sheet-cc"></div>
            <div class="sheet-body">
                <%@include file="/core/header.jsp" %>
            <div class="content-layout">
            <div class="content-layout-row">
                <div class="layout-cell sidebar1">
                <%@include file="/core/configure/configure_sidebar.html" %>
                <div class="block">
                <div class="block-body">
                <div class="blockheader">
                </div>
                <div class="blockcontent">
                <div class="blockcontent-body">
                <div class="cleared"></div>
                </div>
                </div>
                <div class="cleared"></div>
                </div>
                </div>
                <div class="cleared"></div>
                </div>
                <div class="layout-cell content">
                <div class="post">
                    <div class="post-body">
                        <div class="post-inner article" id="sys" >
                            <h2 class="postheader">General Settings</h2>
                            <div class="cleared"></div>
                            <div class="postcontent">
                                <ul>
                                <% 
                                    String err = null;
                                    if ( (err=(String)request.getAttribute("error")) != null ) {
                                %>
                                <li><p style="color: red;"><%=err%></p></li>
                                <% } %>
                                <h2>User Settings:</h2>
                                <h3>Add New User:</h3>
                                <form style="display: inline-block" action="ConfigureSettings" method="post">
                                    <table>
                                        <tr><td><label>Username: </label></td>
                                            <td><input name="username" type=text value=""></td>
                                        </tr>
                                        <tr><td><label>Password: </label></td>
                                            <td><input name="password" type=password value=""></td>
                                        </tr>
                                        <tr><td><label>Email: </label></td>
                                           <td><input name="email" type=text value=""></td>
                                        </tr>
                                    </table>
                                    <input name="new_user" type="submit" value="Add User">
                                </form>
                                <h3>Remove User:</h3>
                                    <form style="display: inline-block" action="ConfigureSettings" method="post">
                                    <table>
                                    <tr><td><label>Username: </label></td>
                                        <td><input name="username" type=text value=""></td>
                                    </tr>
                                    </table>
                                    <input name="remove_user" type="submit" value="Remove User">
                                    </form>
                                </ul>     
                                <ul>        
                                   <h2>Database Settings:</h2>
                                   <h3>Change Database:</h3>
                                    <form style="display: inline-block" action="ConfigureSettings" method="post">
                                        <table>
                                            <tr><td><label>DB name: </label></td>
                                               <td><input name="name" type=text value=""></td>
                                            </tr>
                                            <tr><td><label>DB ip: </label></td>
                                                <td><input name="ip" type=text value=""></td>
                                            </tr>
                                            <tr><td><label>DB port: </label></td>
                                                <td><input name="port" type=text value=""></td>
                                            </tr>
                                            <tr><td><label>DB username: </label></td>
                                               <td><input name="username" type=text value=""></td>
                                            </tr>
                                            <tr><td><label>DB password: </label></td>
                                                <td><input name="password" type=password value=""></td>
                                            </tr>
                                        </table>
                                        <input name="change_database" type="submit" value="Change Database">
                                    </form>
                                </ul>     
                            </div>
                            <div class="cleared"></div>
                        </div>
                        <div class="cleared"></div>
                    </div>
                </div>
                <div class="cleared"></div>
            </div>
            </div>
      </div>
      <div class="cleared"></div>
      <%@include file="/core/footer.jsp" %>
    </div>
    </div>
    <div class="cleared"></div>
    </div>
    </body>
</html>
