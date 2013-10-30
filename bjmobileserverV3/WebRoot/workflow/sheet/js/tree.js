var isRadioPara;//单选多选

//如果是多选,返回选择的数据
			var returnStr = '';//返回选择的字符串集合 格式例如：D:id,name,fullname;U:id,name,loginname;
			function getDepAndUser()//返回选择的部门和人
			{
				returnStr = '';
				var ids = '';
				if(isRadioPara=='0')//单选
					ids = tree.getSelectedItemId();
				if(isRadioPara=='1')//多选
				    ids = tree.getAllChecked();//得到选择的id集合
				if(ids!=''){
					var idArr = ids.split(',');
					for (var i = 0; i < idArr.length; i++) {
					    if(idArr[i].indexOf("_") > 0 )   
      					    idArr[i] = idArr[i].substring(0,idArr[i].indexOf("_"));
      					
      					var pid = tree.getUserData(idArr[i],"id");
						var text = tree.getUserData(idArr[i],"text");
						var orgtype = tree.getUserData(idArr[i],"type");
						var name =  tree.getUserData(idArr[i],"name");
						returnStr += orgtype+':'+pid+','+text+','+name +";";
					}
				}
			}
			
		function insertOneData(id)
		{
			if(id!=''){
				if(id.indexOf("_")!=-1)
				{//含有该字符
					id = id.substr(0,id.indexOf("_"));
				}
				var text = tree.getUserData(id,"text");
				var orgtype = tree.getUserData(id,"type");
				var type = '';
				if(orgtype=="U")
				{
					type = '人';
				}
				else
				{
					type = '部门';
				}
				var parentId = tree.getParentId(id);//获取父节点id
				var parentText = tree.getItemText(parentId);//获取父节点text
				$("#inertData").append("<div id=" + id+" idText="+text+"><b>" + text+"</b> ["+type+"]"+"；<img src=\""+jsCtx+"/common/style/blue/images/del_user.jpg\" onclick='delItem(\""+id+"\")' style=\"margin-left:2px; margin-bottom:-1px;\" alt=\"删除\"></img></div>");
			}
		}
			
		//删除底部对应id数据
		function delOneData(id)
		{
			if(id!=''){
				if(id.indexOf("_")!=-1)
				{//含有该字符
					id = id.substr(0,id.indexOf("_"));
				}
				$("#"+id).remove();
			}
		}
		
		//删除节点,并取消"勾选"
		function delItem(id){
			if(id!=''){//删除底部该条记录
				if(id.indexOf("_")!=-1)
				{//含有该字符
					id = id.substr(0,id.indexOf("_"));
				}
				$("#"+id).remove();
			}
			tree.setCheck(id,'2');
		}
		
		function radioSelect(id) {
			if(isRadioPara=='0') {
				$("#inertData").empty();
				insertOneData(id);
			}
		}
		
		function rearchUserOrDep() {
			$("#inertData").empty();
			$("#treeboxbox_tree").empty();
			$("#tree_loading").text('加载中．．．．．．');
			tree=new dhtmlXTreeObject('treeboxbox_tree','100%','100%',0);
			tree.setImagePath(jsCtx + '/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/');
			tree.enableCheckBoxes(isRadioPara);
			tree.enableTreeLines(true);
			var key = $("#researchTxt").val();
			var url = '';
			if(key == '快速查找......') {
				url = jsCtx + '/TreeProxyServlet';
				tree.setXMLAutoLoading(url);
			} else {
				key = encodeURI(key);
				key = encodeURI(key);
				url = jsCtx + '/TreeProxyServlet?rearchUserOrDep='+key;
			}
			tree.loadXML(url, clearLoading);
			
			tree.setOnClickHandler(function(id){radioSelect(id);});
			tree.setOnCheckHandler(function(id,state){if(state == 1) {insertOneData(id);} else {delOneData(id);}});
		}
		
		function clearLoading() {
			$("#tree_loading").empty();
		}