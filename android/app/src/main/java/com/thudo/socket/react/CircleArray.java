package com.thudo.socket.react;

/**
 * Created by phuongtq on 12/18/2015.
 */
public class CircleArray {


    private byte [] _arrayData;

    private int _writeIndex =0;
    private int _readIndex = 0;

    // init array
    public CircleArray(int size){
        _arrayData = new byte [size];
    }

    public int readData(int numberData, byte [] array){
        if (array.length < numberData){
            FullLog.e("Your array pass to this methode is not enough ");
//            throw new IndexOutOfBoundsException();
            return -1;
        }


        if (numberData > getDataAvailable()){
            FullLog.w("not enough data to read, dataAvailable = " + getDataAvailable());
            return -1;
        }
        else{
            for (int count = 0 ; count < numberData;count ++){

                array[count] = _arrayData[_readIndex];

                _readIndex ++;
                if (_readIndex >= _arrayData.length){
                    _readIndex = 0;
                }
            }
        }

        return 0;
    }

    public byte readOneData(){

        if (getDataAvailable() == 0){
            FullLog.w("not enough data to read, dataAvailable = " + getDataAvailable());
            throw new IndexOutOfBoundsException();
//            return -1;
        }

        return _arrayData[_readIndex++];
    }

    // return native value id false
    public int writeData(byte [] arrayData , int numberData){
        if (arrayData.length < numberData){
            FullLog.e("Your array pass to this methode is not enough ");
//            throw new IndexOutOfBoundsException();
            return -1;
        }

        if (numberData >  ( getDataAvailable())){
            FullLog.w("not enough space to write, space Available = " + (_arrayData.length -  getDataAvailable()));
            return -1;
        }
        else{
            for (int count = 0 ; count < numberData;count ++){

                _arrayData[_writeIndex] = arrayData[count];

                _writeIndex ++;
                if (_writeIndex >= _arrayData.length){
                    _writeIndex = 0;
                }
            }
        }
        return 0;
    }

    public void writeOneData(byte data){
        if ( getDataAvailable() < 1){
            FullLog.w("not enough space to write, spaceAvailable = " + (_arrayData.length - getDataAvailable()));
            throw new IndexOutOfBoundsException();
//            return -1;
        }

        _arrayData[_writeIndex++] = data;

    }




    private int getDataAvailable(){
        if (_readIndex < _writeIndex){
            return (_writeIndex - _readIndex);
        }
        else{
            return (_arrayData.length - _readIndex + _writeIndex);
        }
    }

}
