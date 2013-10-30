var actorMap;
 /**
  *添加派发人事件
  */
 function addDealActor(actionType){
 	actorMap = new Map();
 	getDepAndUser();
 	var actorStr = returnStr;
 	var actors = actorStr.split(';');
 	for(var i=0;i<actors.length-1;i++){
 		var actor = new JsActor();
 		var type = actors[i].substring(0,1);
 		var infos = actors[i].substring(2).split(',');
 		if(type == 'D'){//部门
			actor.type = 'G';
 			actor.id = infos[0];
 			actor.model = '2';
 		}else if(type == 'U'){//用户
			actor.type = 'U';
 			actor.id = infos[2];
 			actor.model = '2';
 		}else if(type == 'R'){
 			actor.type = 'R';
 			actor.id = infos[0];
 			actor.model = '2';
 		}
		actor.name = infos[1];
		
		actor.dealtype = actionType;
		actor.limittimes = '';
		actor.payouttime = '';
		actor.dealtimes = '';
		actor.nextstepid = '';
		actor.payoutdesc = '';
		actor.childschema = '';
		
		actorMap.put(actor.id,actor);
 	}
 	formsubmit();
 }
 
 /**
  *数据提交，将MAP中的数据返回至父页面,拼字符串如下：
  *处理人：U#:huangwei#:ASSIGN#:2#:1274976000#:1274966000#:1274976000#:dp_3#:这里写派发说明。个性化派发说明#;...
  *处理人中文名：张三：李四：神州泰岳...
  */
 function formsubmit(){
 	var payoutcode = '';	//派发人_code
	var payoutname = '';	//派发人_name
	
	var limittime = '';//document.getElementById('limittime').value;
	var dealtime = '';//document.getElementById('dealtime').value;
	var desc = '';//document.getElementById('desc').value;
	for(var i=0;i<actorMap.keySet().size();i++){
		var tempstr = '';
		var actor = actorMap.get(actorMap.keySet().get(i));
			tempstr += actor.type + '#:';
			tempstr += actor.id + '#:';
			tempstr += actor.dealtype + '#:';
			tempstr += actor.model + '#:';
			if(actor.limittimes != ''){//未设置‘处理时限、派发时限或受理时限’的用公用时间；
				tempstr += '' + '#:';
				tempstr += '' + '#:';
			}else{
				tempstr += '' + '#:';
				tempstr += '' + '#:';
			}
			if(actor.dealtimes != ''){
				tempstr += '' + '#:';
			}else{
				tempstr += '' + '#:';
			}
			tempstr += '#:';
			tempstr += actor.nextstepid + '#:';
			tempstr += actor.childschema + '#:';
			tempstr += actor.payoutdesc + '#;';
		
			payoutcode += tempstr;
			payoutname += ';' + actor.name;
	}
	
	if(payoutname != ''){
		payoutname = payoutname.substring(1);
	}
	document.getElementById('dealActorStr.text').value = payoutname;
	document.getElementById('dealActorStr.content').value = payoutcode;
 }
 
 function clearActor() {
 	document.getElementById('dealActorStr.text').value = '';
	document.getElementById('dealActorStr.content').value = '';
 }
 
  