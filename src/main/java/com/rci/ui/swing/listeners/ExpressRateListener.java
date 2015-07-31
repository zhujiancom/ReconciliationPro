package com.rci.ui.swing.listeners;

import com.rci.ui.swing.views.builder.PopWindowBuilder;
import com.rci.ui.swing.views.builder.WindowBuilderFactory;

public class ExpressRateListener extends BaseWinListener {
	@Override
	protected PopWindowBuilder getBuilder() {
		return WindowBuilderFactory.createExpressReateWinBuilder();
	}
}
