$wnd.hal.runAsyncCallback48("function Kch(){Kch=BGc;UBe();Jch=(new lv).Qg((ebh(),Vah),(new lmk(new FUe,ftd(Y3(K3(cjb,1),{4:1,1:1,5:1,6:1},2,6,['scale-down','scale-down-cluster-name','scale-down-connectors','scale-down-discovery-group','scale-down-group-name'])))).Lvc()).Qg((ebh(),Wah),(new lmk(new FUe,ftd(Y3(K3(cjb,1),{4:1,1:1,5:1,6:1},2,6,['backup-port-offset','backup-request-retries','backup-request-retry-interval','max-backups'])))).Lvc()).Qg((ebh(),Zah),(new lmk(new FUe,ftd(Y3(K3(cjb,1),{4:1,1:1,5:1,6:1},2,6,['cluster-name','group-name'])))).Lvc()).Qg((ebh(),$ah),(new lmk(new FUe,ftd(Y3(K3(cjb,1),{4:1,1:1,5:1,6:1},2,6,['cluster-name','group-name','scale-down-cluster-name','scale-down-connectors','scale-down-discovery-group','scale-down-group-name'])))).Lvc()).Qg((ebh(),_ah),(new lmk(new FUe,ftd(Y3(K3(cjb,1),{4:1,1:1,5:1,6:1},2,6,['backup-port-offset','backup-request-retries','backup-request-retry-interval','max-backups'])))).Lvc()).Qg((ebh(),cbh),(new lmk(new FUe,wud('failover-on-server-shutdown'))).Lvc()).Qg((ebh(),dbh),(new lmk(new FUe,ftd(Y3(K3(cjb,1),{4:1,1:1,5:1,6:1},2,6,['scale-down-cluster-name','scale-down-group-name','scale-down-connectors','scale-down-discovery-group'])))).Lvc()).Pg()}\nfunction Mch(a,b){Kch();var d;WBe.call(this,a.f);this.LEb();if(Jch.containsKey(a)){d=x5(Jch.get(a),105);this.B$().XM(d);d.Pvc(b)}}\nzGc(4929,34,{1:1,8:1,34:1},Mch);_.LEb=function Lch(){};var Jch;var KVb=Cgd('org.jboss.hal.client.configuration.subsystem.messaging','HaPolicyPreview',4929,Btc);function Zrh(){Zrh=BGc;tDe()}\nfunction _rh(a,b,d,e,g,h,i,j){Zrh();vDe.call(this,(new Bgk(a,'messaging-server-settings',j.Y$c().KRc())).Mtc(new psh).Qtc(new ssh).Otc(new ysh(j)));this.VHb();this.b=e;this.a=g;this.c=j;this.P_(new Bsh(this,i,h,b,d,j))}\nfunction bsh(a,b,d){Zrh();var e;{if(Lkd(b.Kl(),(VEl(),ACl))){e=b.Vvc().kd().iM(new Qsh(a)).jM();e.gK(new Wsh(b))}else{x5(b.Vvc().getAtIndex(0),241).gvc().BW(b)}}}\nfunction hsh(a,b){Zrh();return Lkd(a.Y$c().KSc(),b.Ll())}\nfunction ish(a,b){Zrh();b.gvc().BW(a)}\nfunction jsh(a){Zrh();return Lkd('messaging-server',a.Yuc())}\nfunction ksh(a){Zrh();return a}\nzGc(3436,37,{1:1,13:1,37:1},_rh);_.VHb=function $rh(){};_.XHb=function csh(a,b,d,e,g,h,i){Zrh();var j,k,l,m,n;{k=new zsd;l=mPd(h.auc().jd(),false).iM(new Zsh).jM().hK(new vsh);if(l.Nb()){m=V8j(H5(l.Mb()),'messaging-server'+'-');n=new Fil(a,new dth(m));j=(t5g(),g5g).resolve(n);b.Ppc(j,'ha-policy',new gth(this,k,d,e,m,g,n,i))}else{i.Ej(k)}}};_.YHb=function dsh(a,b){Zrh();this.WHb(a)};_.ZHb=function esh(a,b,d){Zrh();this.cIb(a,b)};_.$Hb=function fsh(a,b,d){Zrh();d.a.qEb(this.a,a,new jth(this,d))};_._Hb=function gsh(){Zrh();this.E_((chk(),bhk))};_.aIb=function lsh(a,b,d,e,g,h,i,j){Zrh();var k,l,m;{a.add((new rnk('Destinations')).awc((VEl(),zCl)).$vc(b.zvc(d.YAc('messaging-server-destination').cF('server',e)._E())).cwc(new XBe('Destinations',g.a_c().GYc()))._vc());a.add((new rnk('Connections')).awc((VEl(),yCl)).$vc(b.zvc(d.YAc('messaging-server-connection').cF('server',e)._E())).cwc(new XBe('Connections',g.a_c().FYc()))._vc());a.add((new rnk('Clustering')).awc((VEl(),xCl)).$vc(b.zvc(d.YAc('messaging-server-clustering').cF('server',e)._E())).cwc(new XBe('Clustering',g.a_c().EYc()))._vc());k=(new rnk('HA Policy')).awc((VEl(),ACl));if(j.isEmpty()){k.Zvc(g.Y$c().eMc(),new Esh(this,h)).$vc(b.zvc(d.YAc('messaging-server-ha-policy').cF('server',e)._E())).cwc(new XBe('HA Policy',g.a_c().HYc()))}else{l=x5(j.getAtIndex(0),46);m=kbh(l.name);k.$vc(b.zvc(d.YAc('messaging-server-ha-policy').cF('server',e)._E())).Zvc(g.Y$c().dRc(),new Hsh(this,h,m)).cwc(new Mch(m,l.value))}a.add(k._vc());i.Ej(a)}};_.bIb=function msh(a){Zrh();{RTl(this.b,sTl(this.c.$$c().gTc(a.a.f)));this.E_((chk(),bhk))}};_.WHb=function ash(a){(new udh(this.c,new Ksh(this,a))).YEb()};_.cIb=function nsh(a,b){b.tEb(this.a,a,this.c,new Nsh(this))};var vYb=Cgd('org.jboss.hal.client.configuration.subsystem.messaging','ServerSettingsColumn',3436,Fsc);function osh(){osh=BGc}\nfunction psh(){osh()}\nzGc(3437,1,{1:1},psh);_.v0=function qsh(a){return new Mnk(x5(a,120))};var gYb=Cgd('org.jboss.hal.client.configuration.subsystem.messaging','ServerSettingsColumn/0methodref$ctor$Type',3437,Xib);function rsh(){rsh=BGc}\nfunction ssh(){rsh()}\nzGc(3438,1,{1:1},ssh);_.t0=function tsh(a){return x5(a,120).Xvc()};var hYb=Cgd('org.jboss.hal.client.configuration.subsystem.messaging','ServerSettingsColumn/1methodref$getPreviewContent$Type',3438,Xib);function ush(){ush=BGc}\nfunction vsh(){ush()}\nzGc(3443,1,{1:1},vsh);_.Rc=function wsh(a){return x5(a,139).$uc()};var iYb=Cgd('org.jboss.hal.client.configuration.subsystem.messaging','ServerSettingsColumn/2methodref$getItemId$Type',3443,Xib);function xsh(){xsh=BGc}\nfunction ysh(a){xsh();this.a=a}\nzGc(3441,1,{1:1},ysh);_.D8=function zsh(a,b){bsh(this.a,a,b)};var jYb=Cgd('org.jboss.hal.client.configuration.subsystem.messaging','ServerSettingsColumn/lambda$0$Type',3441,Xib);function Ash(){Ash=BGc}\nfunction Bsh(a,b,d,e,g,h){Ash();this.a=a;this.f=b;this.b=d;this.c=e;this.d=g;this.e=h}\nzGc(3448,1,{1:1},Bsh);_.u0=function Csh(a,b){this.a.XHb(this.f,this.b,this.c,this.d,this.e,a,b)};var kYb=Cgd('org.jboss.hal.client.configuration.subsystem.messaging','ServerSettingsColumn/lambda$1$Type',3448,Xib);function Dsh(){Dsh=BGc}\nfunction Esh(a,b){Dsh();this.a=a;this.b=b}\nzGc(3445,1,{1:1},Esh);_.BW=function Fsh(a){this.a.YHb(this.b,a)};var lYb=Cgd('org.jboss.hal.client.configuration.subsystem.messaging','ServerSettingsColumn/lambda$10$Type',3445,Xib);function Gsh(){Gsh=BGc}\nfunction Hsh(a,b,d){Gsh();this.a=a;this.c=b;this.b=d}\nzGc(3446,1,{1:1},Hsh);_.BW=function Ish(a){this.a.ZHb(this.c,this.b,a)};var mYb=Cgd('org.jboss.hal.client.configuration.subsystem.messaging','ServerSettingsColumn/lambda$11$Type',3446,Xib);function Jsh(){Jsh=BGc}\nfunction Ksh(a,b){Jsh();this.a=a;this.b=b}\nzGc(3450,1,{1:1},Ksh);_.fZ=function Lsh(a,b){this.a.$Hb(this.b,a,b)};var nYb=Cgd('org.jboss.hal.client.configuration.subsystem.messaging','ServerSettingsColumn/lambda$2$Type',3450,Xib);function Msh(){Msh=BGc}\nfunction Nsh(a){Msh();this.a=a}\nzGc(3451,1,{1:1,12:1},Nsh);_.Lj=function Osh(){this.a._Hb()};var oYb=Cgd('org.jboss.hal.client.configuration.subsystem.messaging','ServerSettingsColumn/lambda$3$Type',3451,Xib);function Psh(){Psh=BGc}\nfunction Qsh(a){Psh();this.a=a}\nzGc(3439,1,{1:1,28:1},Qsh);_.gL=function Rsh(a){return SId(this,a)};_.hL=function Ssh(){return WId(this)};_.iL=function Tsh(a){return XId(this,a)};_.jL=function Ush(a){return hsh(this.a,a)};var pYb=Cgd('org.jboss.hal.client.configuration.subsystem.messaging','ServerSettingsColumn/lambda$4$Type',3439,Xib);function Vsh(){Vsh=BGc}\nfunction Wsh(a){Vsh();this.a=a}\nzGc(3440,1,{1:1,21:1},Wsh);_.wK=function Xsh(a){ish(this.a,a)};var qYb=Cgd('org.jboss.hal.client.configuration.subsystem.messaging','ServerSettingsColumn/lambda$5$Type',3440,Xib);function Ysh(){Ysh=BGc}\nfunction Zsh(){Ysh()}\nzGc(3442,1,{1:1,28:1},Zsh);_.gL=function $sh(a){return SId(this,a)};_.hL=function _sh(){return WId(this)};_.iL=function ath(a){return XId(this,a)};_.jL=function bth(a){return jsh(a)};var rYb=Cgd('org.jboss.hal.client.configuration.subsystem.messaging','ServerSettingsColumn/lambda$6$Type',3442,Xib);function cth(){cth=BGc}\nfunction dth(a){cth();this.a=a}\nzGc(3444,1,{1:1,44:1},dth);_.Mb=function eth(){return ksh(this.a)};var sYb=Cgd('org.jboss.hal.client.configuration.subsystem.messaging','ServerSettingsColumn/lambda$7$Type',3444,Xib);function fth(){fth=BGc}\nfunction gth(a,b,d,e,g,h,i,j){fth();this.a=a;this.d=b;this.c=d;this.e=e;this.i=g;this.f=h;this.g=i;this.b=j}\nzGc(3447,1,{1:1},gth);_.Q6=function hth(a){this.a.aIb(this.d,this.c,this.e,this.i,this.f,this.g,this.b,a)};var tYb=Cgd('org.jboss.hal.client.configuration.subsystem.messaging','ServerSettingsColumn/lambda$8$Type',3447,Xib);function ith(){ith=BGc}\nfunction jth(a,b){ith();this.a=a;this.b=b}\nzGc(3449,1,{1:1,12:1},jth);_.Lj=function kth(){this.a.bIb(this.b)};var uYb=Cgd('org.jboss.hal.client.configuration.subsystem.messaging','ServerSettingsColumn/lambda$9$Type',3449,Xib);zGc(1732,1,{1:1});_._Ib=function bvh(){var a;a=this.QJb(this.a.LA().hwc(),this.a.LA().jwc(),this.a.PA().aBc(),this.a.Iz().Kw(),this.a.VA().nIc(),this.a.IA().trc(),this.a.XA().eKc(),this.a.bB().l_c());this.yJb(a);return a};_.yJb=function Bvh(a){};_.QJb=function Tvh(a,b,d,e,g,h,i,j){return new _rh(a,b,d,e,g,h,i,j)};zGc(1754,1,{45:1,1:1});_.dk=function swh(){this.b.Ej(this.a.a._Ib())};zGc(241,1,{1:1,241:1});_.gvc=function Jkk(){return this.d};_.Ll=function Kkk(){return this.f};yVl(zJ)(48);\n//# sourceURL=hal-48.js\n")
