sap.ui.define([
    "sap/fe/test/JourneyRunner",
	"hms/managemanagers/managemanagers/test/integration/pages/ManagersList.gen",
	"hms/managemanagers/managemanagers/test/integration/pages/ManagersObjectPage.gen"
], function (JourneyRunner, ManagersListGenerated, ManagersObjectPageGenerated) {
    'use strict';

    const runner = new JourneyRunner({
        launchUrl: sap.ui.require.toUrl('hms/managemanagers/managemanagers') + '/test/flpSandbox.html#hmsmanagemanagersmanagemanager-tile',
        pages: {
			onTheManagersListGenerated: ManagersListGenerated,
			onTheManagersObjectPageGenerated: ManagersObjectPageGenerated
        },
        async: true
    });

    return runner;
});

