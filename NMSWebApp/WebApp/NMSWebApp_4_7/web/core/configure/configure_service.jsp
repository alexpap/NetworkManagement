<%@page import="java.util.Hashtable"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="db.Service"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>NMSWebApp</title>
        <style>
            <%@include file="/core/css/style.css" %>
        </style>
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
                            <div class="post-inner article" id="serv" >
                                <h2 class="postheader">Service configuration</h2>
                                <div class="cleared"></div>
                                <div class="postcontent">
                                    <ul>
                                        <%Hashtable<Integer,Service> services = (Hashtable<Integer,Service>)request.getAttribute("services");
                                        if (!services.isEmpty()){
                                        %>
                                        <h3>Set up Services</h3>
                                    <li><table>    
                                          <tr> 
                                              <th>Name </th> 
                                              <th>Description </th> 
                                              <th>OID </th>
                                              <th>Type</th>
                                              <th>Action </th> 
                                          </tr>
                                          <% 
                                                Iterator<Map.Entry<Integer,Service>> it1 = services.entrySet().iterator();
                                                while ( it1.hasNext() ) {
                                                    Map.Entry<Integer,Service> entry = it1.next();
                                                    Service service = entry.getValue(); 
                                          %>
                                          <tr> 
                                            <td><%= service.getName() %>          </td> 
                                            <td><%= service.getDesc() %>   </td>
                                            <td><%= service.getOid()%>            </td>
                                            <td><%= service.getType()%>           </td>
                                            <td>
                                                <div style="display: inline-block">
                                                    <form style="display: inline-block" action="ConfigureService" method="post">
                                                        <input hidden type="text" name="service_id" value=<%= service.getId()  %> />
                                                        <input name="service_edit" type="submit" value="Edit">
                                                    </form>
                                                        <form style="display: inline-block" action="ConfigureService" method="post">
                                                        <input hidden type="text" name="service_id" value=<%= service.getId()  %> />                                                        
                                                        <input name="service_remove" type="submit" value="Remove">  
                                                    </form>
                                                </div>
                                            </td> 
                                           </tr>                                               
                                           <%
                                                }   }else{                                       
                                            %>
                                            <h3>Please start by adding a service.</h3>
                                            <%}%>
                                      </table></li>
                                      <li><form action="ConfigureService" method="post"><input name="service_add" type="submit" value="Add New Service" ></form></li>
                                    </ul>     
                                </div>
                                <div class="cleared"></div>
                            </div>
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
