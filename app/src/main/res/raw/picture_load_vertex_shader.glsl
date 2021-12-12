uniform mat4 uMVPMatrix;
uniform mat4 uSTMatrix;
attribute vec4 a_Position;
attribute vec2 a_TextureCoordinates;

varying vec2 v_TextureCoordinates;

void main()                    
{                            
    v_TextureCoordinates = (uSTMatrix * vec4(a_TextureCoordinates,0.0,1.0)).xy;
    gl_Position = uMVPMatrix * a_Position;
//    gl_Position =  a_Position;
//    v_TextureCoordinates = a_TextureCoordinates;
}          