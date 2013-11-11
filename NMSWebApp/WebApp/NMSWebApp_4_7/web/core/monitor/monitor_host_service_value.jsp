<%@page import="db.Service"%>
<%@page import="java.util.Map"%>
<%@page import="db.Host"%>
<%@page import="db.Value"%>
<%@page import="java.util.Hashtable"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
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
            <script language="javascript" type="text/javascript" src="/core/js/jquery.js"></script>
            <script language="javascript" type="text/javascript" src="/core/js/jquery.flot.js"></script>
    </head>
    <body>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js" type="text/javascript"></script>
        <script src="http://code.highcharts.com/highcharts.js"></script>
        <script src="http://code.highcharts.com/modules/exporting.js"></script>
        <script src="http://code.highcharts.com/modules/data.js"></script>
        <script>
          $(function () {
              $('#container').highcharts({
                  data: {
                      table: document.getElementById('datatable')
                  },
                  chart: {
                      type: 'line'
                  },
                  title: {
                      text: 'Service Values Graph'
                  },
                  tooltip: {
                      formatter: function() {
                          return '<b>'+ this.series.name +'</b><br/>'+
                              this.y +' '+ this.x.toLowerCase();
                      }
                  }
              });
          });
        </script>
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
              <div class="vmenublock">
                <div class="vmenublock-body">
                  <div class="vmenublockheader">
                      <h3 class="t" id="sys">System Monitor</h3>
                  </div>
                    <%@include file="/core/monitor/monitor_sidebar.html" %>

                </div>
              </div>
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
                   <div class="post-inner article" id="sys">
                       <%
                           Host host= (Host)request.getAttribute("host");
                           Service service = (Service)request.getAttribute("service");
                           
                       %>
                        <h2 class="postheader">List of values of <%=service.getName() %> at <%=host.getName() %></h2>
                        <div class="cleared"></div>
                        <div class="postcontent">
                            <ul>
                                <li>                        
                                  <table id="datatable" >
                                      <thead>
                                       <tr>
                                        <th>Timestamp</th>
                                         <th><%= service.getName() %> Value</th>
                                       </tr>
                                       </thead>
                                       <tbody>
                                <% 
                                    ArrayList<Value> values = (ArrayList<Value>)request.getAttribute("values");
                                    for (Iterator<Value> it = values.iterator(); it.hasNext();){
                                        Value v = it.next();                                        
                                %>
                                        <tr>
                                          <td><%= v.getTimestamp().getTime() %> </td>
                                          <td><%= v.getValue()%>     </td> 
                                        </tr>
                                <%
                                    }
                                 %>
                                 </tbody>
                                </table>
                                <div id="container" style="min-width: 400px; height: 400px; margin: 0 auto"></div>
                                </li>
                                
                                </li>
                             </ul>
                       </div>
                       <div class="cleared"></div>
                    </div>
                    <div class="cleared"></div>
              </div>
            </div>
            <div class="cleared"></div>
            <div id="container" hidden style="width:100%; height:400px;"></div>
            <div class="cleared"></div>
          </div>
        </div>
      </div>
      <div class="cleared"></div>
          <div id="placeholder" style="width:600px;height:300px;"></div>
      <div class="cleared"></div>
      <%@include file="/core/footer.jsp" %>
    </div>
  </div>
  <div class="cleared"></div>
  </div>
  </body>
</html>

