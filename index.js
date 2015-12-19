/** @file ....js
 *  @brief ....js source file.
 *
 *  File/module comments.
 *
 *  @author Phuongtq
 *	@mobile 01684499886
 *  @note No Note at the moment
 *  @bug No known bugs.
 *
 * <pre>
 * MODIFICATION HISTORY:
 *
 * Ver   Who  	  Date       Changes
 * ----- --------- ---------- -----------------------------------------------
 * 1.00  Phuongtq ../../.... First release
 *
 *
 *</pre>
 ******************************************************************************/

'use strict';

var { NativeModules } = require('react-native');
var CBuffer = require('CBuffer');

var NativePlainSocket = NativeModules.ReactPlainSocket;

var ginputBuffer = new CBuffer(1048576 * 2);

/** @brief call back function when receive data
 *
 *  @param serverAddr , port
 *  @return Void.
 *  @note
 */
function connectToServer(serverAddr :String,port : int){
  NativePlainSocket.init(serverAddr,port);
  DeviceEventEmitter.addListener('ReactSocketModule_onDataReceiver', onReceiverData);
  NativePlainSocket.connect();
}

function disconnect(){
  NativePlainSocket.disconnect();
}


/** @brief handler event from native when receive data
 *
 *  @param
 *  @return Void.
 *  @note
 */
function onReceiverData(e: Event){

  var readData = new Promise(function(success,error){
    console.log( " onReceiverData : " + e.DataReceiver.length) ;
    var dataReceiver = e.DataReceiver;

  // push byte to cbuffer
     for (var i = 0; i < dataReceiver.length; ++i) {
       ginputBuffer.push(dataReceiver.charCodeAt(i) > 127 ?
         dataReceiver.charCodeAt(i) - 256 :
         dataReceiver.charCodeAt(i));
     }
    NativePlainSocket.releaseEventSignal();
    success();
  });

  readData.then(parseFrame);
}


/** @brief parse frame from input buffer
 *
 *  @param
 *  @return Void.
 *  @note
 */

 function parseFrame(){

 }


 /** @brief parse
  *
  *  @param
  *  @return Void.
  *  @note
  */
  function parseFrame(){

  }


const


// module.exports = NativeModules.ReactPlainSocket;
