<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>NMSWebApp</title>
        <style>
            <%@include file="/core/css/style.css" %>
        </style>
    </head>
    <body>
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
                                    <h2 class="postheader">Service Addition</h2>
                                    <div class="cleared"></div>
                                    <div class="postcontent">
                                      <ul><form action="ConfigureService" method="post">
                                        <li>
                                            <table>
                                                <tr><td>Name: </td>
                                                    <td><input  name="service_name" type="text" size="30" value="" /></td></tr>
                                                <tr><td>Oid: </td>
                                                    <td><input  name="service_oid" type="text" size="30" value="" /></td></tr>
                                                <tr><td>Type:  </td>
                                                    <td><input  name="service_type" type="text" size="30" value="" /></td></tr>
                                                <tr><td>Description:  </td>
                                                    <td><input  name="service_desc" type="text" size="30" value="" /></td></tr> 
                                            </table></li> 
                                        <input name="service_added" type="submit" value="Apply"/>
                                      </form></ul>
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