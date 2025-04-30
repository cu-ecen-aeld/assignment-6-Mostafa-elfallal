LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-Mostafa-elfallal.git;protocol=ssh;branch=main \
           file://misc_init \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "048e3515bdecb3bc687b1ea2d45678229d0d5593"

S = "${WORKDIR}/git/misc-modules"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"
RPROVIDES:${PN} += "kernel-module-faulty kernel-module-hello"
inherit update-rc.d
INITSCRIPT_NAME = "misc_init"
INITSCRIPT_PARAMS = "start 99 5 2 . stop 20 0 1 6 ."
do_install:append () {
	install -d ${D}${INIT_D_DIR}
	install -m 0755 ${WORKDIR}/${INITSCRIPT_NAME} ${D}${INIT_D_DIR}/
}
FILES:${PN} += "${INIT_D_DIR}/${INITSCRIPT_NAME}"