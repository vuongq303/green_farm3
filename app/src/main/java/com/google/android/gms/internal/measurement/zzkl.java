package com.google.android.gms.internal.measurement;

import com.gameloft.adsmanager.JavaUtils;
import com.gameloft.glads.GLURLConnection;

/* JADX INFO: loaded from: classes.dex */
public final class zzkl extends zzabd<zzkl> {
    private static volatile zzkl[] zzatb;
    public Integer zzatc = null;
    public zzki[] zzatd = zzki.zzld();
    public zzkn[] zzate = zzkn.zzlg();
    public Long zzatf = null;
    public Long zzatg = null;
    public Long zzath = null;
    public Long zzati = null;
    public Long zzatj = null;
    public String zzatk = null;
    public String zzatl = null;
    public String zzatm = null;
    public String zzafl = null;
    public Integer zzatn = null;
    public String zzado = null;
    public String zztd = null;
    public String zztc = null;
    public Long zzato = null;
    public Long zzatp = null;
    public String zzatq = null;
    public Boolean zzatr = null;
    public String zzadg = null;
    public Long zzats = null;
    public Integer zzatt = null;
    public String zzaef = null;
    public String zzadh = null;
    public Boolean zzatu = null;
    public zzkh[] zzatv = zzkh.zzlc();
    public String zzadj = null;
    public Integer zzatw = null;
    private Integer zzatx = null;
    private Integer zzaty = null;
    public String zzatz = null;
    public Long zzaua = null;
    public Long zzaub = null;
    public String zzauc = null;
    private String zzaud = null;
    public Integer zzaue = null;

    public zzkl() {
        this.zzbzh = null;
        this.zzbzs = -1;
    }

    public static zzkl[] zzlf() {
        if (zzatb == null) {
            synchronized (zzabh.zzbzr) {
                if (zzatb == null) {
                    zzatb = new zzkl[0];
                }
            }
        }
        return zzatb;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkl)) {
            return false;
        }
        zzkl zzklVar = (zzkl) obj;
        if (this.zzatc == null) {
            if (zzklVar.zzatc != null) {
                return false;
            }
        } else if (!this.zzatc.equals(zzklVar.zzatc)) {
            return false;
        }
        if (zzabh.equals(this.zzatd, zzklVar.zzatd) && zzabh.equals(this.zzate, zzklVar.zzate)) {
            if (this.zzatf == null) {
                if (zzklVar.zzatf != null) {
                    return false;
                }
            } else if (!this.zzatf.equals(zzklVar.zzatf)) {
                return false;
            }
            if (this.zzatg == null) {
                if (zzklVar.zzatg != null) {
                    return false;
                }
            } else if (!this.zzatg.equals(zzklVar.zzatg)) {
                return false;
            }
            if (this.zzath == null) {
                if (zzklVar.zzath != null) {
                    return false;
                }
            } else if (!this.zzath.equals(zzklVar.zzath)) {
                return false;
            }
            if (this.zzati == null) {
                if (zzklVar.zzati != null) {
                    return false;
                }
            } else if (!this.zzati.equals(zzklVar.zzati)) {
                return false;
            }
            if (this.zzatj == null) {
                if (zzklVar.zzatj != null) {
                    return false;
                }
            } else if (!this.zzatj.equals(zzklVar.zzatj)) {
                return false;
            }
            if (this.zzatk == null) {
                if (zzklVar.zzatk != null) {
                    return false;
                }
            } else if (!this.zzatk.equals(zzklVar.zzatk)) {
                return false;
            }
            if (this.zzatl == null) {
                if (zzklVar.zzatl != null) {
                    return false;
                }
            } else if (!this.zzatl.equals(zzklVar.zzatl)) {
                return false;
            }
            if (this.zzatm == null) {
                if (zzklVar.zzatm != null) {
                    return false;
                }
            } else if (!this.zzatm.equals(zzklVar.zzatm)) {
                return false;
            }
            if (this.zzafl == null) {
                if (zzklVar.zzafl != null) {
                    return false;
                }
            } else if (!this.zzafl.equals(zzklVar.zzafl)) {
                return false;
            }
            if (this.zzatn == null) {
                if (zzklVar.zzatn != null) {
                    return false;
                }
            } else if (!this.zzatn.equals(zzklVar.zzatn)) {
                return false;
            }
            if (this.zzado == null) {
                if (zzklVar.zzado != null) {
                    return false;
                }
            } else if (!this.zzado.equals(zzklVar.zzado)) {
                return false;
            }
            if (this.zztd == null) {
                if (zzklVar.zztd != null) {
                    return false;
                }
            } else if (!this.zztd.equals(zzklVar.zztd)) {
                return false;
            }
            if (this.zztc == null) {
                if (zzklVar.zztc != null) {
                    return false;
                }
            } else if (!this.zztc.equals(zzklVar.zztc)) {
                return false;
            }
            if (this.zzato == null) {
                if (zzklVar.zzato != null) {
                    return false;
                }
            } else if (!this.zzato.equals(zzklVar.zzato)) {
                return false;
            }
            if (this.zzatp == null) {
                if (zzklVar.zzatp != null) {
                    return false;
                }
            } else if (!this.zzatp.equals(zzklVar.zzatp)) {
                return false;
            }
            if (this.zzatq == null) {
                if (zzklVar.zzatq != null) {
                    return false;
                }
            } else if (!this.zzatq.equals(zzklVar.zzatq)) {
                return false;
            }
            if (this.zzatr == null) {
                if (zzklVar.zzatr != null) {
                    return false;
                }
            } else if (!this.zzatr.equals(zzklVar.zzatr)) {
                return false;
            }
            if (this.zzadg == null) {
                if (zzklVar.zzadg != null) {
                    return false;
                }
            } else if (!this.zzadg.equals(zzklVar.zzadg)) {
                return false;
            }
            if (this.zzats == null) {
                if (zzklVar.zzats != null) {
                    return false;
                }
            } else if (!this.zzats.equals(zzklVar.zzats)) {
                return false;
            }
            if (this.zzatt == null) {
                if (zzklVar.zzatt != null) {
                    return false;
                }
            } else if (!this.zzatt.equals(zzklVar.zzatt)) {
                return false;
            }
            if (this.zzaef == null) {
                if (zzklVar.zzaef != null) {
                    return false;
                }
            } else if (!this.zzaef.equals(zzklVar.zzaef)) {
                return false;
            }
            if (this.zzadh == null) {
                if (zzklVar.zzadh != null) {
                    return false;
                }
            } else if (!this.zzadh.equals(zzklVar.zzadh)) {
                return false;
            }
            if (this.zzatu == null) {
                if (zzklVar.zzatu != null) {
                    return false;
                }
            } else if (!this.zzatu.equals(zzklVar.zzatu)) {
                return false;
            }
            if (!zzabh.equals(this.zzatv, zzklVar.zzatv)) {
                return false;
            }
            if (this.zzadj == null) {
                if (zzklVar.zzadj != null) {
                    return false;
                }
            } else if (!this.zzadj.equals(zzklVar.zzadj)) {
                return false;
            }
            if (this.zzatw == null) {
                if (zzklVar.zzatw != null) {
                    return false;
                }
            } else if (!this.zzatw.equals(zzklVar.zzatw)) {
                return false;
            }
            if (this.zzatx == null) {
                if (zzklVar.zzatx != null) {
                    return false;
                }
            } else if (!this.zzatx.equals(zzklVar.zzatx)) {
                return false;
            }
            if (this.zzaty == null) {
                if (zzklVar.zzaty != null) {
                    return false;
                }
            } else if (!this.zzaty.equals(zzklVar.zzaty)) {
                return false;
            }
            if (this.zzatz == null) {
                if (zzklVar.zzatz != null) {
                    return false;
                }
            } else if (!this.zzatz.equals(zzklVar.zzatz)) {
                return false;
            }
            if (this.zzaua == null) {
                if (zzklVar.zzaua != null) {
                    return false;
                }
            } else if (!this.zzaua.equals(zzklVar.zzaua)) {
                return false;
            }
            if (this.zzaub == null) {
                if (zzklVar.zzaub != null) {
                    return false;
                }
            } else if (!this.zzaub.equals(zzklVar.zzaub)) {
                return false;
            }
            if (this.zzauc == null) {
                if (zzklVar.zzauc != null) {
                    return false;
                }
            } else if (!this.zzauc.equals(zzklVar.zzauc)) {
                return false;
            }
            if (this.zzaud == null) {
                if (zzklVar.zzaud != null) {
                    return false;
                }
            } else if (!this.zzaud.equals(zzklVar.zzaud)) {
                return false;
            }
            if (this.zzaue == null) {
                if (zzklVar.zzaue != null) {
                    return false;
                }
            } else if (!this.zzaue.equals(zzklVar.zzaue)) {
                return false;
            }
            if (this.zzbzh == null || this.zzbzh.isEmpty()) {
                return zzklVar.zzbzh == null || zzklVar.zzbzh.isEmpty();
            }
            return this.zzbzh.equals(zzklVar.zzbzh);
        }
        return false;
    }

    public final int hashCode() {
        int iHashCode = 0;
        int iHashCode2 = ((this.zzaue == null ? 0 : this.zzaue.hashCode()) + (((this.zzaud == null ? 0 : this.zzaud.hashCode()) + (((this.zzauc == null ? 0 : this.zzauc.hashCode()) + (((this.zzaub == null ? 0 : this.zzaub.hashCode()) + (((this.zzaua == null ? 0 : this.zzaua.hashCode()) + (((this.zzatz == null ? 0 : this.zzatz.hashCode()) + (((this.zzaty == null ? 0 : this.zzaty.hashCode()) + (((this.zzatx == null ? 0 : this.zzatx.hashCode()) + (((this.zzatw == null ? 0 : this.zzatw.hashCode()) + (((this.zzadj == null ? 0 : this.zzadj.hashCode()) + (((((this.zzatu == null ? 0 : this.zzatu.hashCode()) + (((this.zzadh == null ? 0 : this.zzadh.hashCode()) + (((this.zzaef == null ? 0 : this.zzaef.hashCode()) + (((this.zzatt == null ? 0 : this.zzatt.hashCode()) + (((this.zzats == null ? 0 : this.zzats.hashCode()) + (((this.zzadg == null ? 0 : this.zzadg.hashCode()) + (((this.zzatr == null ? 0 : this.zzatr.hashCode()) + (((this.zzatq == null ? 0 : this.zzatq.hashCode()) + (((this.zzatp == null ? 0 : this.zzatp.hashCode()) + (((this.zzato == null ? 0 : this.zzato.hashCode()) + (((this.zztc == null ? 0 : this.zztc.hashCode()) + (((this.zztd == null ? 0 : this.zztd.hashCode()) + (((this.zzado == null ? 0 : this.zzado.hashCode()) + (((this.zzatn == null ? 0 : this.zzatn.hashCode()) + (((this.zzafl == null ? 0 : this.zzafl.hashCode()) + (((this.zzatm == null ? 0 : this.zzatm.hashCode()) + (((this.zzatl == null ? 0 : this.zzatl.hashCode()) + (((this.zzatk == null ? 0 : this.zzatk.hashCode()) + (((this.zzatj == null ? 0 : this.zzatj.hashCode()) + (((this.zzati == null ? 0 : this.zzati.hashCode()) + (((this.zzath == null ? 0 : this.zzath.hashCode()) + (((this.zzatg == null ? 0 : this.zzatg.hashCode()) + (((this.zzatf == null ? 0 : this.zzatf.hashCode()) + (((((((this.zzatc == null ? 0 : this.zzatc.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31) + zzabh.hashCode(this.zzatd)) * 31) + zzabh.hashCode(this.zzate)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31) + zzabh.hashCode(this.zzatv)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31;
        if (this.zzbzh != null && !this.zzbzh.isEmpty()) {
            iHashCode = this.zzbzh.hashCode();
        }
        return iHashCode2 + iHashCode;
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    protected final int zza() {
        int iZza = super.zza();
        if (this.zzatc != null) {
            iZza += zzabb.zzf(1, this.zzatc.intValue());
        }
        if (this.zzatd != null && this.zzatd.length > 0) {
            int iZzb = iZza;
            for (int i = 0; i < this.zzatd.length; i++) {
                zzki zzkiVar = this.zzatd[i];
                if (zzkiVar != null) {
                    iZzb += zzabb.zzb(2, zzkiVar);
                }
            }
            iZza = iZzb;
        }
        if (this.zzate != null && this.zzate.length > 0) {
            int iZzb2 = iZza;
            for (int i2 = 0; i2 < this.zzate.length; i2++) {
                zzkn zzknVar = this.zzate[i2];
                if (zzknVar != null) {
                    iZzb2 += zzabb.zzb(3, zzknVar);
                }
            }
            iZza = iZzb2;
        }
        if (this.zzatf != null) {
            iZza += zzabb.zzc(4, this.zzatf.longValue());
        }
        if (this.zzatg != null) {
            iZza += zzabb.zzc(5, this.zzatg.longValue());
        }
        if (this.zzath != null) {
            iZza += zzabb.zzc(6, this.zzath.longValue());
        }
        if (this.zzatj != null) {
            iZza += zzabb.zzc(7, this.zzatj.longValue());
        }
        if (this.zzatk != null) {
            iZza += zzabb.zzd(8, this.zzatk);
        }
        if (this.zzatl != null) {
            iZza += zzabb.zzd(9, this.zzatl);
        }
        if (this.zzatm != null) {
            iZza += zzabb.zzd(10, this.zzatm);
        }
        if (this.zzafl != null) {
            iZza += zzabb.zzd(11, this.zzafl);
        }
        if (this.zzatn != null) {
            iZza += zzabb.zzf(12, this.zzatn.intValue());
        }
        if (this.zzado != null) {
            iZza += zzabb.zzd(13, this.zzado);
        }
        if (this.zztd != null) {
            iZza += zzabb.zzd(14, this.zztd);
        }
        if (this.zztc != null) {
            iZza += zzabb.zzd(16, this.zztc);
        }
        if (this.zzato != null) {
            iZza += zzabb.zzc(17, this.zzato.longValue());
        }
        if (this.zzatp != null) {
            iZza += zzabb.zzc(18, this.zzatp.longValue());
        }
        if (this.zzatq != null) {
            iZza += zzabb.zzd(19, this.zzatq);
        }
        if (this.zzatr != null) {
            this.zzatr.booleanValue();
            iZza += zzabb.zzas(20) + 1;
        }
        if (this.zzadg != null) {
            iZza += zzabb.zzd(21, this.zzadg);
        }
        if (this.zzats != null) {
            iZza += zzabb.zzc(22, this.zzats.longValue());
        }
        if (this.zzatt != null) {
            iZza += zzabb.zzf(23, this.zzatt.intValue());
        }
        if (this.zzaef != null) {
            iZza += zzabb.zzd(24, this.zzaef);
        }
        if (this.zzadh != null) {
            iZza += zzabb.zzd(25, this.zzadh);
        }
        if (this.zzati != null) {
            iZza += zzabb.zzc(26, this.zzati.longValue());
        }
        if (this.zzatu != null) {
            this.zzatu.booleanValue();
            iZza += zzabb.zzas(28) + 1;
        }
        if (this.zzatv != null && this.zzatv.length > 0) {
            for (int i3 = 0; i3 < this.zzatv.length; i3++) {
                zzkh zzkhVar = this.zzatv[i3];
                if (zzkhVar != null) {
                    iZza += zzabb.zzb(29, zzkhVar);
                }
            }
        }
        if (this.zzadj != null) {
            iZza += zzabb.zzd(30, this.zzadj);
        }
        if (this.zzatw != null) {
            iZza += zzabb.zzf(31, this.zzatw.intValue());
        }
        if (this.zzatx != null) {
            iZza += zzabb.zzf(32, this.zzatx.intValue());
        }
        if (this.zzaty != null) {
            iZza += zzabb.zzf(33, this.zzaty.intValue());
        }
        if (this.zzatz != null) {
            iZza += zzabb.zzd(34, this.zzatz);
        }
        if (this.zzaua != null) {
            iZza += zzabb.zzc(35, this.zzaua.longValue());
        }
        if (this.zzaub != null) {
            iZza += zzabb.zzc(36, this.zzaub.longValue());
        }
        if (this.zzauc != null) {
            iZza += zzabb.zzd(37, this.zzauc);
        }
        if (this.zzaud != null) {
            iZza += zzabb.zzd(38, this.zzaud);
        }
        return this.zzaue != null ? iZza + zzabb.zzf(39, this.zzaue.intValue()) : iZza;
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    public final void zza(zzabb zzabbVar) throws zzabc {
        if (this.zzatc != null) {
            zzabbVar.zze(1, this.zzatc.intValue());
        }
        if (this.zzatd != null && this.zzatd.length > 0) {
            for (int i = 0; i < this.zzatd.length; i++) {
                zzki zzkiVar = this.zzatd[i];
                if (zzkiVar != null) {
                    zzabbVar.zza(2, zzkiVar);
                }
            }
        }
        if (this.zzate != null && this.zzate.length > 0) {
            for (int i2 = 0; i2 < this.zzate.length; i2++) {
                zzkn zzknVar = this.zzate[i2];
                if (zzknVar != null) {
                    zzabbVar.zza(3, zzknVar);
                }
            }
        }
        if (this.zzatf != null) {
            zzabbVar.zzb(4, this.zzatf.longValue());
        }
        if (this.zzatg != null) {
            zzabbVar.zzb(5, this.zzatg.longValue());
        }
        if (this.zzath != null) {
            zzabbVar.zzb(6, this.zzath.longValue());
        }
        if (this.zzatj != null) {
            zzabbVar.zzb(7, this.zzatj.longValue());
        }
        if (this.zzatk != null) {
            zzabbVar.zzc(8, this.zzatk);
        }
        if (this.zzatl != null) {
            zzabbVar.zzc(9, this.zzatl);
        }
        if (this.zzatm != null) {
            zzabbVar.zzc(10, this.zzatm);
        }
        if (this.zzafl != null) {
            zzabbVar.zzc(11, this.zzafl);
        }
        if (this.zzatn != null) {
            zzabbVar.zze(12, this.zzatn.intValue());
        }
        if (this.zzado != null) {
            zzabbVar.zzc(13, this.zzado);
        }
        if (this.zztd != null) {
            zzabbVar.zzc(14, this.zztd);
        }
        if (this.zztc != null) {
            zzabbVar.zzc(16, this.zztc);
        }
        if (this.zzato != null) {
            zzabbVar.zzb(17, this.zzato.longValue());
        }
        if (this.zzatp != null) {
            zzabbVar.zzb(18, this.zzatp.longValue());
        }
        if (this.zzatq != null) {
            zzabbVar.zzc(19, this.zzatq);
        }
        if (this.zzatr != null) {
            zzabbVar.zza(20, this.zzatr.booleanValue());
        }
        if (this.zzadg != null) {
            zzabbVar.zzc(21, this.zzadg);
        }
        if (this.zzats != null) {
            zzabbVar.zzb(22, this.zzats.longValue());
        }
        if (this.zzatt != null) {
            zzabbVar.zze(23, this.zzatt.intValue());
        }
        if (this.zzaef != null) {
            zzabbVar.zzc(24, this.zzaef);
        }
        if (this.zzadh != null) {
            zzabbVar.zzc(25, this.zzadh);
        }
        if (this.zzati != null) {
            zzabbVar.zzb(26, this.zzati.longValue());
        }
        if (this.zzatu != null) {
            zzabbVar.zza(28, this.zzatu.booleanValue());
        }
        if (this.zzatv != null && this.zzatv.length > 0) {
            for (int i3 = 0; i3 < this.zzatv.length; i3++) {
                zzkh zzkhVar = this.zzatv[i3];
                if (zzkhVar != null) {
                    zzabbVar.zza(29, zzkhVar);
                }
            }
        }
        if (this.zzadj != null) {
            zzabbVar.zzc(30, this.zzadj);
        }
        if (this.zzatw != null) {
            zzabbVar.zze(31, this.zzatw.intValue());
        }
        if (this.zzatx != null) {
            zzabbVar.zze(32, this.zzatx.intValue());
        }
        if (this.zzaty != null) {
            zzabbVar.zze(33, this.zzaty.intValue());
        }
        if (this.zzatz != null) {
            zzabbVar.zzc(34, this.zzatz);
        }
        if (this.zzaua != null) {
            zzabbVar.zzb(35, this.zzaua.longValue());
        }
        if (this.zzaub != null) {
            zzabbVar.zzb(36, this.zzaub.longValue());
        }
        if (this.zzauc != null) {
            zzabbVar.zzc(37, this.zzauc);
        }
        if (this.zzaud != null) {
            zzabbVar.zzc(38, this.zzaud);
        }
        if (this.zzaue != null) {
            zzabbVar.zze(39, this.zzaue.intValue());
        }
        super.zza(zzabbVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzabj
    public final /* synthetic */ zzabj zzb(zzaba zzabaVar) throws zzabi {
        while (true) {
            int iZzvo = zzabaVar.zzvo();
            switch (iZzvo) {
                case 0:
                    break;
                case 8:
                    this.zzatc = Integer.valueOf(zzabaVar.zzvs());
                    break;
                case 18:
                    int iZzb = zzabm.zzb(zzabaVar, 18);
                    int length = this.zzatd == null ? 0 : this.zzatd.length;
                    zzki[] zzkiVarArr = new zzki[iZzb + length];
                    if (length != 0) {
                        System.arraycopy(this.zzatd, 0, zzkiVarArr, 0, length);
                    }
                    while (length < zzkiVarArr.length - 1) {
                        zzkiVarArr[length] = new zzki();
                        zzabaVar.zza(zzkiVarArr[length]);
                        zzabaVar.zzvo();
                        length++;
                    }
                    zzkiVarArr[length] = new zzki();
                    zzabaVar.zza(zzkiVarArr[length]);
                    this.zzatd = zzkiVarArr;
                    break;
                case 26:
                    int iZzb2 = zzabm.zzb(zzabaVar, 26);
                    int length2 = this.zzate == null ? 0 : this.zzate.length;
                    zzkn[] zzknVarArr = new zzkn[iZzb2 + length2];
                    if (length2 != 0) {
                        System.arraycopy(this.zzate, 0, zzknVarArr, 0, length2);
                    }
                    while (length2 < zzknVarArr.length - 1) {
                        zzknVarArr[length2] = new zzkn();
                        zzabaVar.zza(zzknVarArr[length2]);
                        zzabaVar.zzvo();
                        length2++;
                    }
                    zzknVarArr[length2] = new zzkn();
                    zzabaVar.zza(zzknVarArr[length2]);
                    this.zzate = zzknVarArr;
                    break;
                case 32:
                    this.zzatf = Long.valueOf(zzabaVar.zzvt());
                    break;
                case 40:
                    this.zzatg = Long.valueOf(zzabaVar.zzvt());
                    break;
                case 48:
                    this.zzath = Long.valueOf(zzabaVar.zzvt());
                    break;
                case 56:
                    this.zzatj = Long.valueOf(zzabaVar.zzvt());
                    break;
                case 66:
                    this.zzatk = zzabaVar.readString();
                    break;
                case 74:
                    this.zzatl = zzabaVar.readString();
                    break;
                case 82:
                    this.zzatm = zzabaVar.readString();
                    break;
                case JavaUtils.Constants.TABLET_HEIGHT_DP /* 90 */:
                    this.zzafl = zzabaVar.readString();
                    break;
                case 96:
                    this.zzatn = Integer.valueOf(zzabaVar.zzvs());
                    break;
                case 106:
                    this.zzado = zzabaVar.readString();
                    break;
                case 114:
                    this.zztd = zzabaVar.readString();
                    break;
                case 130:
                    this.zztc = zzabaVar.readString();
                    break;
                case 136:
                    this.zzato = Long.valueOf(zzabaVar.zzvt());
                    break;
                case 144:
                    this.zzatp = Long.valueOf(zzabaVar.zzvt());
                    break;
                case 154:
                    this.zzatq = zzabaVar.readString();
                    break;
                case 160:
                    this.zzatr = Boolean.valueOf(zzabaVar.zzvr());
                    break;
                case 170:
                    this.zzadg = zzabaVar.readString();
                    break;
                case 176:
                    this.zzats = Long.valueOf(zzabaVar.zzvt());
                    break;
                case 184:
                    this.zzatt = Integer.valueOf(zzabaVar.zzvs());
                    break;
                case 194:
                    this.zzaef = zzabaVar.readString();
                    break;
                case GLURLConnection.HTTP_ACCEPTED /* 202 */:
                    this.zzadh = zzabaVar.readString();
                    break;
                case 208:
                    this.zzati = Long.valueOf(zzabaVar.zzvt());
                    break;
                case 224:
                    this.zzatu = Boolean.valueOf(zzabaVar.zzvr());
                    break;
                case 234:
                    int iZzb3 = zzabm.zzb(zzabaVar, 234);
                    int length3 = this.zzatv == null ? 0 : this.zzatv.length;
                    zzkh[] zzkhVarArr = new zzkh[iZzb3 + length3];
                    if (length3 != 0) {
                        System.arraycopy(this.zzatv, 0, zzkhVarArr, 0, length3);
                    }
                    while (length3 < zzkhVarArr.length - 1) {
                        zzkhVarArr[length3] = new zzkh();
                        zzabaVar.zza(zzkhVarArr[length3]);
                        zzabaVar.zzvo();
                        length3++;
                    }
                    zzkhVarArr[length3] = new zzkh();
                    zzabaVar.zza(zzkhVarArr[length3]);
                    this.zzatv = zzkhVarArr;
                    break;
                case 242:
                    this.zzadj = zzabaVar.readString();
                    break;
                case 248:
                    this.zzatw = Integer.valueOf(zzabaVar.zzvs());
                    break;
                case 256:
                    this.zzatx = Integer.valueOf(zzabaVar.zzvs());
                    break;
                case 264:
                    this.zzaty = Integer.valueOf(zzabaVar.zzvs());
                    break;
                case 274:
                    this.zzatz = zzabaVar.readString();
                    break;
                case 280:
                    this.zzaua = Long.valueOf(zzabaVar.zzvt());
                    break;
                case 288:
                    this.zzaub = Long.valueOf(zzabaVar.zzvt());
                    break;
                case 298:
                    this.zzauc = zzabaVar.readString();
                    break;
                case 306:
                    this.zzaud = zzabaVar.readString();
                    break;
                case 312:
                    this.zzaue = Integer.valueOf(zzabaVar.zzvs());
                    break;
                default:
                    if (!super.zza(zzabaVar, iZzvo)) {
                    }
                    break;
            }
        }
        return this;
    }
}
