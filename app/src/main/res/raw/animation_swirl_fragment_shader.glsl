precision mediump float;

uniform sampler2D u_TextureUnit;
varying vec2 v_TextureCoordinates;
uniform highp float alpha;

// 根据进度转换旋转角度
vec2 calculationAngleProgress(vec2 p0, vec2 p1, vec2 p2, vec2 p3, float t)
{
    float tInv = 1.0-t;
    float tInv2 = pow(tInv, 2.0);
    float tInv3 = pow(tInv, 3.0);
    float t2 = pow(t, 2.0);
    float t3 = pow(t, 3.0);
    vec2 p = p0*tInv3+3.0*p1*t*tInv2+3.0*p2*t2*tInv+p3*t3;
    return p;
}
// 旋转
vec4 spinColor(sampler2D sampler, vec2 uv, vec2 center, float zoom, float radius, float angle)
{
    vec2 tc= uv*inputSize;
    tc-=center;
    float dist=length(tc);
    if (dist<radius)
    {
        float percent=(radius-dist)/radius;
        float theta = percent*radians(angle);
        if (progress<0.5)
        theta = -theta;
        float s=sin(theta);
        float c=cos(theta);
        tc= vec2(dot(tc, vec2(c, -s)), dot(tc, vec2(s, c)));
    }
    tc*=1.0-zoom*.5;
    tc+=center;
    tc=tc/inputSize;
    return texture2D(sampler, tc);
}


void main()
{
    gl_FragColor = texture2D(u_TextureUnit, v_TextureCoordinates)*alpha;
}