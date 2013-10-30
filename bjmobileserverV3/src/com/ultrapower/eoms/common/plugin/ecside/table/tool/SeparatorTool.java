﻿/*
 * Copyright 2006-2007 original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ultrapower.eoms.common.plugin.ecside.table.tool;

import com.ultrapower.eoms.common.plugin.ecside.core.TableModel;
import com.ultrapower.eoms.common.plugin.ecside.util.HtmlBuilder;

/**
 * @author Wei Zijun
 *
 */

public class SeparatorTool extends BaseTool {
	
	public SeparatorTool(){
		super();
	}
	
	public SeparatorTool(HtmlBuilder html,TableModel model) {
		super(html,model);
	}
	
    @Override
	public void buildTool() {
    	getHtmlBuilder().td(1).styleClass("separatorTool").close();
        getHtmlBuilder().nbsp();
        getHtmlBuilder().tdEnd();
    }

}
