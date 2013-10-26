
package org.uqbar.mingus;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class MingusStandaloneSetup extends MingusStandaloneSetupGenerated{

	public static void doSetup() {
		new MingusStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

