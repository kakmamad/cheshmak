var exec = require('cordova/exec');
module.exports = {
    addTag: function (tagName) {
        exec(
            function (result) {
                //console.log('AddTag Success.');
            },
            null,
            'CheshmakCordovaPlugin',
            'addTag', [tagName]
        );
    },
    deleteTag: function (tagName) {
        exec(
            function (result) {
                //console.log('DeleteTag Success.');
            },
            null,
            'CheshmakCordovaPlugin',
            'deleteTag', [tagName]
        );
    },
    deleteAllTags: function () {
        exec(
            function (result) {
                //console.log('DeleteAllTags Success.');
            },
            null,
            'CheshmakCordovaPlugin',
            'deleteAllTags', []
        );
    },
    startView: function () {
        exec(
            function (result) {
                //console.log('StartView Success.');
            },
            null,
            'CheshmakCordovaPlugin',
            'startView', []
        );
    },
    stopView: function () {
        exec(
            function (result) {
                //console.log('StopView Success.');
            },
            null,
            'CheshmakCordovaPlugin',
            'stopView', []
        );
    },

    // setTest: function (test) {
    //     exec(
    //         function (result) {
    //             //console.log('SetTest Success.');
    //         },
    //         null,
    //         'CheshmakCordovaPlugin',
    //         'setTest', [test]
    //     );
    // },
    getData: function (success) {
        exec(
            success,
            null,
            'CheshmakCordovaPlugin',
            'getData', []
        );
    },
    showInterstitialAds: function () {
        exec(
          function (result) {

          },
          null,
          'CheshmakCordovaPlugin',
          'showInterstitialAds', []
        );
    },
    disableAdvertises: function () {
        exec(
          function (result) {

          },
          null,
          'CheshmakCordovaPlugin',
          'disableAdvertises', []
        );
    },
    enableAdvertises: function () {
        exec(
          function (result) {

          },
          null,
          'CheshmakCordovaPlugin',
          'enableAdvertises', []
        );
    },
    getInterstitialEvents: function (success) {
        exec(
          success,
          null,
          'CheshmakCordovaPlugin',
          'getInterstitialEvents', []
        );
    },


};