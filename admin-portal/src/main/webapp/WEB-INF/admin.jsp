<%
    String usertype="";
    if(request.getParameter("utype")!=null){
        usertype=request.getParameter("utype").toString();
    }
%>
<%@ include file="nav.html" %>
<%@ include file="eventMaster.html" %>
<%@ include file="PriceMaster.html" %>
<%@ include file="EventPriceConfig.html" %>
<%@ include file="userregister.html" %>

</div>
</body>
</html>
